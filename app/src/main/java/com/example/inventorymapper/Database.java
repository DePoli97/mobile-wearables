package com.example.inventorymapper;

import com.example.inventorymapper.ui.model.Household;
import com.example.inventorymapper.ui.model.Item;
import com.example.inventorymapper.ui.model.Location;
import com.google.firebase.database.DatabaseReference;

public final class Database {

    // Static DatabaseReference, shared across all usages
    private static final DatabaseReference mDatabase = MyApplication.getDatabaseReference();

    // Private constructor to prevent instantiation
    private Database() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }

    public static Household addHousehold(Household household) {
        DatabaseReference householdRef = mDatabase.child("households").push();
        householdRef.setValue(household);
        household.setId(householdRef.getKey());
        return household;
    }

    public static Location addLocation(Location location, String houseId) {
        DatabaseReference locationRef = mDatabase.child("households")
                .child(houseId)
                .child("location")
                .child("sublocations")
                .push();
        locationRef.setValue(location);
        location.setId(locationRef.getKey());

        return location;
    }

    public static void addItem(Item item) {
        DatabaseReference itemRef = mDatabase.child("items").push();
        itemRef.setValue(item);
    }

    public static void addItem(String itemName, String itemDescription, String locationId) {
        Item item = new Item(itemName, itemDescription);
        DatabaseReference itemRef = mDatabase.child("locations").child(locationId).push();
        itemRef.setValue(item);
    }

    public static DatabaseReference getAllItems() {
        return mDatabase.child("items");
    }
}
