package com.example.matador.service;

import com.example.matador.model.TouristAttraction;
import com.example.matador.repository.TouristRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TouristServiceTest {

    @Mock
    private TouristRepository repository;

    @InjectMocks
    private TouristService service;

    @Test
    void getTouristAttractions_delegatesToRepository() {
        List<TouristAttraction> expected = List.of(new TouristAttraction());
        when(repository.findAll()).thenReturn(expected);

        List<TouristAttraction> result = service.getTouristAttractions();

        assertSame(expected, result);
        verify(repository).findAll();
    }

    @Test
    void getTouristAttractionByName_delegatesToRepository() {
        TouristAttraction expected = new TouristAttraction();
        when(repository.getTouristAttractionByName("Tivoli")).thenReturn(expected);

        TouristAttraction result = service.getTouristAttractionByName("Tivoli");

        assertSame(expected, result);
        verify(repository).getTouristAttractionByName("Tivoli");
    }

    @Test
    void getTouristAttractionByName_returnsNullWhenNotFound() {
        when(repository.getTouristAttractionByName("Unknown")).thenReturn(null);

        TouristAttraction result = service.getTouristAttractionByName("Unknown");

        assertNull(result);
    }

    @Test
    void addTouristAttraction_resolvesLocationIdBeforeAdding() {
        TouristAttraction attraction = new TouristAttraction();
        attraction.setLocation("Rødovrevej");
        when(repository.getLocationIdByName("Rødovrevej")).thenReturn(1);
        when(repository.addTouristAttraction(attraction)).thenReturn(attraction);

        service.addTouristAttraction(attraction);

        assertEquals(1, attraction.getLocationId());
        verify(repository).getLocationIdByName("Rødovrevej");
        verify(repository).addTouristAttraction(attraction);
    }

    @Test
    void deleteByName_delegatesToRepository() {
        service.deleteByName("Tivoli");

        verify(repository).deleteByName("Tivoli");
    }

    @Test
    void update_resolvesLocationIdThenDelegates() {
        TouristAttraction attraction = new TouristAttraction();
        attraction.setLocation("Hvidovrevej");
        when(repository.getLocationIdByName("Hvidovrevej")).thenReturn(2);

        service.update(attraction);

        assertEquals(2, attraction.getLocationId());
        verify(repository).getLocationIdByName("Hvidovrevej");
        verify(repository).update(attraction);
    }

    @Test
    void getAllTags_delegatesToRepository() {
        List<String> tags = List.of("Børnevenlig", "Gratis");
        when(repository.getAllTags()).thenReturn(tags);

        List<String> result = service.getAllTags();

        assertSame(tags, result);
        verify(repository).getAllTags();
    }

    @Test
    void getLocations_delegatesToRepository() {
        List<String> locations = List.of("Rødovrevej", "Hvidovrevej");
        when(repository.getLocations()).thenReturn(locations);

        List<String> result = service.getLocations();

        assertSame(locations, result);
        verify(repository).getLocations();
    }
}
