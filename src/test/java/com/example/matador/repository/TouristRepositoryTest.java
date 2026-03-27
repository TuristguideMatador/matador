package com.example.matador.repository;

import com.example.matador.model.TouristAttraction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "spring.datasource.url=jdbc:h2:mem:repodb")
@Transactional
class TouristRepositoryTest {

    @Autowired
    private TouristRepository repository;

    @Test
    void findAll_returnsAllAttractions() {
        List<TouristAttraction> attractions = repository.findAll();

        assertEquals(3, attractions.size());
    }

    @Test
    void findAll_populatesTagsForEachAttraction() {
        List<TouristAttraction> attractions = repository.findAll();

        TouristAttraction tivoli = attractions.stream()
                .filter(a -> "Tivoli".equals(a.getName()))
                .findFirst()
                .orElseThrow();

        assertFalse(tivoli.getTags().isEmpty());
        assertTrue(tivoli.getTags().contains("Kultur"));
        assertTrue(tivoli.getTags().contains("Børnevenlig"));
    }

    @Test
    void findAll_includesLocationAndColor() {
        List<TouristAttraction> attractions = repository.findAll();

        attractions.forEach(a -> {
            assertNotNull(a.getLocation());
            assertNotNull(a.getColorHex());
        });
    }

    @Test
    void getTouristAttractionByName_returnsCorrectAttraction() {
        TouristAttraction result = repository.getTouristAttractionByName("Tivoli");

        assertNotNull(result);
        assertEquals("Tivoli", result.getName());
        assertNotNull(result.getLocation());
        assertNotNull(result.getColorHex());
        assertFalse(result.getTags().isEmpty());
    }

    @Test
    void getTouristAttractionByName_returnsNullForUnknownName() {
        TouristAttraction result = repository.getTouristAttractionByName("Nonexistent");

        assertNull(result);
    }

    @Test
    void getLocationIdByName_returnsCorrectId() {
        int locationId = repository.getLocationIdByName("Rødovrevej");

        assertEquals(1, locationId);
    }

    @Test
    void getLocations_returnsAllLocations() {
        List<String> locations = repository.getLocations();

        assertFalse(locations.isEmpty());
        assertTrue(locations.contains("Rødovrevej"));
        assertTrue(locations.contains("Hvidovrevej"));
    }

    @Test
    void getAllTags_returnsAllTags() {
        List<String> tags = repository.getAllTags();

        assertEquals(10, tags.size());
        assertTrue(tags.contains("Børnevenlig"));
        assertTrue(tags.contains("Gratis"));
    }

//    @Test
//    void addTouristAttraction_persistsAttractionWithTags() {
//        TouristAttraction newAttraction = new TouristAttraction();
//        newAttraction.setName("Ny Attraktion");
//        newAttraction.setDescription("En beskrivelse");
//        newAttraction.setTags(List.of("Børnevenlig", "Gratis"));
//
//        repository.addTouristAttraction(newAttraction);
//
//        TouristAttraction saved = repository.getTouristAttractionByName("Ny Attraktion");
//        assertNotNull(saved);
//        assertEquals("En beskrivelse", saved.getDescription());
//        assertTrue(saved.getTags().contains("Børnevenlig"));
//        assertTrue(saved.getTags().contains("Gratis"));
//    }

    @Test
    void deleteByName_removesAttractionAndTags() {
        assertNotNull(repository.getTouristAttractionByName("Tivoli"));

        repository.deleteByName("Tivoli");

        assertNull(repository.getTouristAttractionByName("Tivoli"));
    }

    @Test
    void deleteByName_doesNothingForUnknownName() {
        int initialCount = repository.findAll().size();

        repository.deleteByName("Nonexistent");

        assertEquals(initialCount, repository.findAll().size());
    }

    @Test
    void update_changesAttractionDetails() {
        TouristAttraction tivoli = repository.getTouristAttractionByName("Tivoli");
        tivoli.setDescription("Opdateret beskrivelse");
        tivoli.setTags(List.of("Gratis"));

        repository.update(tivoli);

        TouristAttraction updated = repository.getTouristAttractionByName("Tivoli");
        assertEquals("Opdateret beskrivelse", updated.getDescription());
        assertEquals(List.of("Gratis"), updated.getTags());
    }

    @Test
    void update_replacesAllTags() {
        TouristAttraction tivoli = repository.getTouristAttractionByName("Tivoli");
        List<String> originalTags = tivoli.getTags();
        assertFalse(originalTags.isEmpty());

        tivoli.setTags(List.of("Natur"));
        repository.update(tivoli);

        TouristAttraction updated = repository.getTouristAttractionByName("Tivoli");
        assertEquals(1, updated.getTags().size());
        assertEquals("Natur", updated.getTags().get(0));
    }
}
