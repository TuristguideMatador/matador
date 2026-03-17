package com.example.matador.repository;

import com.example.matador.model.Tags;
import com.example.matador.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TouristRepository {

    private final List<TouristAttraction> touristAttractions = new ArrayList<>();

    /**
     * Tilføjer hardcoded turist attraktioner for test
     */
    public void populateTouristAttractions() {
        List<Tags> tags = new ArrayList<>();

        touristAttractions.add(new TouristAttraction(
                "Toftegårds Bodega",
                "Et af de sidste bastioner som endnu ikke er top gentrificeret. Dog siger rygtet at bartenderen" +
                        " er ret ubehagelig",
                "Valby Langgade",
                List.of(Tags.KULTUR, Tags.INDENDØRS, Tags.KULTUR, Tags.UNDERHOLDNING)
        ));

        touristAttractions.add(new TouristAttraction(
                "BT Kiosk",
                "Ikonisk arkitektur med mange år på bagen. Køb en avis eller en dårlig kop kaffe",
                "Trianglen",
                List.of(Tags.KULTUR, Tags.TOILET)
        ));

        touristAttractions.add(new TouristAttraction(
                "Tivoli",
                "En klassiker udi morskab og spas - tag gerne lidt jyske dollars med",
                "Bernstorffsvej",
                List.of(Tags.KULTUR, Tags.RESTAURANT, Tags.BØRNEVENLIG, Tags.TOILET, Tags.UDENDØRS)
        ));

    }

    public TouristRepository() {
        populateTouristAttractions();
    }

    // getter
    public List<TouristAttraction> getTouristAttractions() { return touristAttractions; }

    // getter til navn
    public TouristAttraction getTouristAttractionByName(String name) {
        for (TouristAttraction touristAttraction : touristAttractions) {
            if (touristAttraction.getName().equalsIgnoreCase(name)) {
                return touristAttraction;
            }
        }
        return null;
    }

    // tilføjer turistattraktion
    public TouristAttraction addTouristAttraction(TouristAttraction touristAttraction) { touristAttractions.add(touristAttraction); return touristAttraction; }

    // opdaterer kun navn
    public void updateTouristAttractionName (TouristAttraction touristAttraction, String name) {
        if (name != null) {
            touristAttraction.setName(name);
        }
    }

    // opdaterer beskrivelse
    public void updateTouristAttractionDescription (TouristAttraction attraction, String description) {
        if (description != null) {
            attraction.setDescription(description);
        }
    }

    // remove by name
    public void deleteByName(String name) {
        TouristAttraction attractionToDelete = getTouristAttractionByName(name);
        int index = this.touristAttractions.indexOf(attractionToDelete);
        touristAttractions.remove(index);

    }


    /**
     * Denne metoder opretter et TreeSet med
     * alle vores nuværende lokationer i touristAttractions
     * listen.
     * @return <b>Locations</b>
     */
    public Set<String> getLocations() {
        Set<String> locations = new TreeSet<>();
        for (TouristAttraction element : touristAttractions) {
            var locationItem = element.getLocation();
            locations.add(locationItem);
        }
        return locations;
    }


    /**
     * Tager et touristAttraction objekt som argument
     * og bruger det til at finde et eksisterende turist objekt i vores repository.
     * Derefter tager vi objektets index og gemmer værdien af det i en int.
     * Derfra kalder vi set metoden og opdaterer objektet direkte in place.
     * @param updatedTouristAttraction
     */
    public void update(TouristAttraction updatedTouristAttraction) {
        TouristAttraction existingAttraction = getTouristAttractionByName(updatedTouristAttraction.getName());
        if(existingAttraction != null) {
            int index = this.touristAttractions.indexOf(existingAttraction);
            this.touristAttractions.set(index, updatedTouristAttraction);
        }
    }
}
