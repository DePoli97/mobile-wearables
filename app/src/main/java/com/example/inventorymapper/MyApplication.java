package com.example.inventorymapper;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.FirebaseApp;


// Qui vanno tutte le configurazioni per l'applicatione come Firebase, permessi, ecc...

public class MyApplication extends Application {
    private static FirebaseDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Optionally, set Firebase Database persistence (for offline support)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        // Initialize the static database variable
        String url = "https://inventorymapper-1234-default-rtdb.europe-west1.firebasedatabase.app/";
        database = FirebaseDatabase.getInstance(url);
    }

    public static DatabaseReference getDatabaseReference() {
        return database.getReference();
    }
}
