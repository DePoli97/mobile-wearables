package com.example.inventorymapper;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.FirebaseApp;


// Qui vanno tutte le configurazioni per l'applicatione come Firebase, permessi, ecc...

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Optionally, set Firebase Database persistence (for offline support)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        // Write a message to the database
        String url = "https://inventorymapper-1234-default-rtdb.europe-west1.firebasedatabase.app/";
        FirebaseDatabase database = FirebaseDatabase.getInstance(url);
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }
}
