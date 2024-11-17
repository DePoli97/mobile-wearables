package com.example.inventorymapper;

import com.example.inventorymapper.model.Item;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHelper {

    // Reference to Firebase Realtime Database
    private DatabaseReference mDatabase;

    public DatabaseHelper() {
        // Initialize the database reference
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    // Method to add a new item to the database
    public void addItem(String itemName, String itemDescription) {
        Item item = new Item(itemName, itemDescription);
        mDatabase.child("items").push().setValue(item);
    }

    // Method to get a reference to all items in the database
    public DatabaseReference getAllItems() {
        return mDatabase.child("items");
    }

}
