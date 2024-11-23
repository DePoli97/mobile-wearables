package com.example.inventorymapper;

import com.example.inventorymapper.ui.model.Item;
import com.google.firebase.database.DatabaseReference;

public final class Database {

    // Static DatabaseReference, shared across all usages
    private static final DatabaseReference mDatabase = MyApplication.getDatabaseReference();

    // Private constructor to prevent instantiation
    private Database() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }

    public static void addItem(Item item) {
        DatabaseReference itemRef = mDatabase.child("items").push();
        itemRef.setValue(item);
    }

    public static void addItem(String itemName, String itemDescription) {
        Item item = new Item(itemName, itemDescription);
        DatabaseReference itemRef = mDatabase.child("items").push();
        itemRef.setValue(item);
    }

    public static DatabaseReference getAllItems() {
        return mDatabase.child("items");
    }
}
