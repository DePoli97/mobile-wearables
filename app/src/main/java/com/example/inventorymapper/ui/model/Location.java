package com.example.inventorymapper.ui.model;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private String id;
    private String name;
    private String description;
    // TODO: consider using maps on id's to avoid having to deal with linear searches
    private List<Location> sublocations;
    private List<Item> items;

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
        this.sublocations = new ArrayList<>();
    }

    public Location(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sublocations = new ArrayList<>();
    }

    public Location(String id, String name, String description, List<Location> sublocations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sublocations = sublocations;
    }

    public Location addSublocation(Location loc) {
        // update location in-place
        if (hasLocationWithId(loc.id)) {
            return this.sublocations.stream()
                    .filter(array_loc -> array_loc.id.equals(loc.id))
                    .findAny().get(); // we already checked that a location with the id is present.
        }

        this.sublocations.add(loc);
        return loc;
    }

    public void removeSublocationWithId(String id) {
        this.sublocations.removeIf(loc -> loc.id.equals(id));
    }

    public boolean hasLocationWithId(String id) {
        return this.sublocations.stream()
                .anyMatch(array_loc -> array_loc.id.equals(id));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
