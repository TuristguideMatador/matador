package com.example.matador.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TouristAttraction {
    private int attraction_id;
    private int location_id;
    private String name;
    private String description;
    private String location;
    private List<String> tags = new ArrayList<>();
    private String colorHex;

    // SQL mapping
    public TouristAttraction(int attractionId,
                             int locationId,
                             String name,
                             String description) {
        this.attraction_id = attractionId;
        this.location_id = locationId;
        this.name = name;
        this.description = description;
    }

    // full
    public TouristAttraction(int attraction_id,
                             int location_id,
                             String name,
                             String description,
                             String location,
                             List<String> tags,
                             String colorHex){
        this.attraction_id = attraction_id;
        this.location_id = location_id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.tags = tags;
        this.colorHex = colorHex;
    }

    // empty
    public TouristAttraction() {}

    // getters
    public int getAttractionId() { return attraction_id; }
    public int getLocationId() { return location_id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getLocation() { return  location; }
    public List<String> getTags() { return tags; }
    public String getColorHex() { return colorHex; }

    // setters
    public void setAttractionId(int attraction_id) { this.attraction_id = attraction_id; }
    public void setLocationId(int location_id) { this.location_id = location_id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setLocation(String location) { this.location = location; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public void setColorHex(String colorHex) { this.colorHex = colorHex; }

    // tilføj tag
//    public void addTags(Tags tag) { tags.add(tag); }

    // tilføjer flere tags ?
    // spørg underviser


    // fjern tag
//    public void removeTags(Tags tag) { tags.remove(tag);}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TouristAttraction that = (TouristAttraction) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(location, that.location) && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, location, tags);
    }


    @Override
    public String toString() {
        return "Name: " + name + ", Description: " + description;
    }


}
