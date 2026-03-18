package com.example.matador.service;

import com.example.matador.model.Tags;
import com.example.matador.model.TouristAttraction;
import com.example.matador.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Service
public class TouristService {

    private final TouristRepository repository;

    public TouristService(TouristRepository repository) {
        this.repository = repository;
    }

    public List<TouristAttraction> getTouristAttractions(){
        return repository.findAll();

    }

    public TouristAttraction getTouristAttractionByName(String name) {
        return repository.getTouristAttractionByName(name);
    }

    public TouristAttraction addTouristAttraction(TouristAttraction touristAttraction){
        return repository.addTouristAttraction(touristAttraction);
    }


    // evt slettes?
    public TouristAttraction updateTouristAttractionName(TouristAttraction touristAttraction, String name) {
        repository.updateTouristAttractionName(touristAttraction, name);
        touristAttraction.setName(name);
        return touristAttraction;
    }

    // evt slettes?
    public TouristAttraction updateTouristAttractionDescription(TouristAttraction touristAttraction, String description) {
        repository.updateTouristAttractionDescription(touristAttraction, description);
        touristAttraction.setDescription(description);
        return touristAttraction;
    }

    public void deleteByName(String name){
        repository.deleteByName(name);
    }


    public void update(TouristAttraction updatedTouristAttraction) {
        repository.update(updatedTouristAttraction);
    }

    /**
     * Opretter en liste af vores eksisterende Tags
     * og returnerer dem
     * @return allEnums
     */
    public List<Tags> getTags() {
        List<Tags> allEnums;
        allEnums = new ArrayList<>(EnumSet.allOf(Tags.class));
        return allEnums;
    }


    public List<String> getLocations() {
        return repository.getLocations();
    }



}
