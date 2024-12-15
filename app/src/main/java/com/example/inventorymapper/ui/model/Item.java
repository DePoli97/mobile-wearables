package com.example.inventorymapper.ui.model;

public class Item {
    private String id;
    private String name;
    private String description;
    private String photoUri;

    // No-argument constructor required by Firebase
    public Item() {
    }

    // Constructor without ID (used for new items)
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Item(String name, String description, String photoUri) {
        this.name = name;
        this.description = description;
        this.photoUri = photoUri;
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

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getPhotoUri() {
        return photoUri;
    }
}
