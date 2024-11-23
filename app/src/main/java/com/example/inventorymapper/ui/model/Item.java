package com.example.inventorymapper.ui.model;

public class Item {
    private String id;
    private String name;
    private String description;

    // No-argument constructor required by Firebase
    public Item() {
    }

    // Constructor without ID (used for new items)
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Constructor with ID
    public Item(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
