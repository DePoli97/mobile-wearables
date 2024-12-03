package com.example.inventorymapper.ui.model;

import java.util.List;

public class Household {
    private String id;
    private String name;
    private Location location;
    private List<User> users;
    private double latitude;
    private double longitude;

    public Household() {

    }

    public Household(String id, String name, Location location, List<User> users, android.location.Location gpsLocation) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.latitude = gpsLocation.getLatitude();
        this.longitude = gpsLocation.getLongitude();

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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
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

}

