package com.example.matador.controller;

import com.example.matador.model.TouristAttraction;
import com.example.matador.service.TouristService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
public class TouristController {

    private final TouristService service;
    public TouristController(TouristService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/attractions")
    public String getAllAttractions(Model model) {
        List<TouristAttraction> touristAttractions = service.getTouristAttractions();
        model.addAttribute("touristAttractions", touristAttractions);
        return "attractionList";
    }

    @GetMapping("/attractions/add")
    public String showTouristAttractionCreationForm(Model model){
        TouristAttraction touristAttraction = new TouristAttraction();
        model.addAttribute("TouristAttraction", touristAttraction);
        model.addAttribute("allTags",service.getAllTags());
        model.addAttribute("allLocations", service.getLocations());
        return "attraction-creation-form";
    }

    @PostMapping("/attractions/add")
    public String register(@ModelAttribute TouristAttraction touristAttraction) {
        service.addTouristAttraction(touristAttraction);
        return "redirect:/attractions";
    }

    @GetMapping("/{name}/tags")
    public String getTagsForTouristAttraction(@PathVariable String name, Model model) {
        TouristAttraction attraction = service.getTouristAttractionByName(name);
        List<String> userTags = attraction.getTags();
        model.addAttribute("name", name);
        model.addAttribute("tags", userTags);
        return "tags";
    }

    @PostMapping("/attractions/delete/{name}")
    public String deleteByName(@PathVariable String name) {
        service.deleteByName(name);
       return "redirect:/attractions";
    }

    @GetMapping("/attractions/getDelete/{name}")
    public String getDeleteByName(@PathVariable String name, Model model) {
        TouristAttraction touristAttraction = service.getTouristAttractionByName(name);
        model.addAttribute("attractionToDelete", touristAttraction);
        return "confirmDelete";
    }

    @GetMapping("/attractions/edit/{name}")
    public String getUpdateAttraction(@PathVariable String name, Model model) {
        TouristAttraction myAttraction = service.getTouristAttractionByName(name);
        model.addAttribute("attraction", myAttraction);
        model.addAttribute("allLocations", service.getLocations());
        model.addAttribute("allTags", service.getAllTags());
        return "updateAttraction";
    }

    @PostMapping("/attractions/edit/{name}")
    public String updateAttraction(@ModelAttribute TouristAttraction updatedTouristAttraction) {
        service.update(updatedTouristAttraction);
        return "redirect:/attractions";
    }
}

