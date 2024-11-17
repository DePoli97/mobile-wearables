package com.example.inventorymapper.model;

public class Item {
    private String name;
    private String description;

    // No-argument constructor required for Firebase
    public Item() {
    }

    // Constructor with arguments
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
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
