package com.example.matador.service;

import com.example.matador.model.TouristAttraction;
import com.example.matador.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {

    private final TouristRepository repository;

    public TouristService(TouristRepository repository) {
        this.repository = repository;
    }

    public List<TouristAttraction> getTouristAttractions() {
        return repository.findAll();

    }

    public TouristAttraction getTouristAttractionByName(String name) {
        return repository.getTouristAttractionByName(name);
    }

    public TouristAttraction addTouristAttraction(TouristAttraction touristAttraction) {
        touristAttraction.setLocationId(repository.getLocationIdByName(touristAttraction.getLocation()));
        return repository.addTouristAttraction(touristAttraction);
    }

    public void deleteByName(String name){
        repository.deleteByName(name);
    }

    public void update(TouristAttraction updatedTouristAttraction) {
        int locationId = repository.getLocationIdByName(updatedTouristAttraction.getLocation());
        updatedTouristAttraction.setLocationId(locationId);
        repository.update(updatedTouristAttraction);
    }

    public List<String> getAllTags() {
        return repository.getAllTags();
    }


    public List<String> getLocations() {
        return repository.getLocations();
    }
}
