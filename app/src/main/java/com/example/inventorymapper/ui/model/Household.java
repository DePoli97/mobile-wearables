package com.example.inventorymapper.ui.model;

import java.util.List;

public class Household {
    private String id;
    private String name;
    private Location location;
    // TODO: understand how to use locations in android
    // likely need a location helper, which decides which localization service/provider
    //    (GPS, Network,...) to use based on user preference/availability/requirements
    // - e.g, to sort households by nearest location network is fine
    // - to add a household, more precise localisation might be required, especially for more rural locations.
    // Docs: https://developer.android.com/reference/android/location/package-summary
    android.location.Location gpsLocation;
    private List<User> users;

    public Household(String id, String name, Location location, List<User> users) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.users = users;
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

    public Location getLocation() {
        return location;
    }

    public List<User> getUsers() {
        return users;
    }
}

