package com.example.matador.model;

import java.util.ArrayList;
import java.util.List;

public class TouristAttraction {
    private int attractionId;
    private int locationId;
    private String name;
    private String description;
    private String location;
    private List<String> tags = new ArrayList<>();
    private String colorHex;


    public TouristAttraction() {}

    // SQL mapping2
    public TouristAttraction(int attractionId,
                             int locationId,
                             String name,
                             String description,
                             String location,
                             String colorHex) {
        this.attractionId = attractionId;
        this.locationId = locationId;
        this.name = name;
        this.description = description;
        this.location = location;
        this.colorHex = colorHex;
    }
    // full
    public TouristAttraction(int attractionId,
                             int locationId,
                             String name,
                             String description,
                             String location,
                             List<String> tags,
                             String colorHex){
        this.attractionId = attractionId;
        this.locationId = locationId;
        this.name = name;
        this.description = description;
        this.location = location;
        this.tags = tags;
        this.colorHex = colorHex;
    }

    public int getAttractionId() { return attractionId; }
    public int getLocationId() { return locationId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getLocation() { return  location; }
    public List<String> getTags() { return tags; }
    public String getColorHex() { return colorHex; }


    public void setAttractionId(int attractionId) { this.attractionId = attractionId; }
    public void setLocationId(int locationId) { this.locationId = locationId; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setLocation(String location) { this.location = location; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public void setColorHex(String colorHex) { this.colorHex = colorHex; }


    @Override
    public String toString() {
        return "Name: " + name + ", Description: " + description;
    }


}
