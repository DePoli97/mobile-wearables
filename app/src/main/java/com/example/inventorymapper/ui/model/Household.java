package com.example.inventorymapper.ui.model;

import java.util.List;

public class Household {
    private String id;
    private String name;
    private Location location;
    private List<User> users;
    // TODO: understand how to use locations in android
    // likely need a location helper, which decides which localization service/provider
    //    (GPS, Network,...) to use based on user preference/availability/requirements
    // - e.g, to sort households by nearest location network is fine
    // - to add a household, more precise localisation might be required, especially for more rural locations.
    // Docs: https://developer.android.com/reference/android/location/package-summary
    android.location.Location gpsLocation;

    public Household(String id, String name, Location location, List<User> users, android.location.Location gpsLocation) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.gpsLocation = gpsLocation;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public android.location.Location getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(android.location.Location gpsLocation) {
        this.gpsLocation = gpsLocation;
    }
}

