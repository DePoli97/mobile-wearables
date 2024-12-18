package com.example.inventorymapper;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


// Qui vanno tutte le configurazioni per l'applicatione come Firebase, permessi, ecc...

public class MyApplication extends Application {
    private static FirebaseDatabase database;
    private static FirebaseStorage image_database;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Optionally, set Firebase Database persistence (for offline support)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        // Initialize the static database variable

//      TODO: url as env variable!
//        String url = BuildConfig.FIREBASE_DATABASE_URL; // take env variable from sys local.properties

        String url = "https://inventorymapper-1234-default-rtdb.europe-west1.firebasedatabase.app/";
        database = FirebaseDatabase.getInstance(url);
        database.setPersistenceEnabled(true);


        String url_images = "gs://inventorymapper-1234.firebasestorage.app";
        image_database = FirebaseStorage.getInstance(url_images);
    }

    public static DatabaseReference getDatabaseReference() {
        return database.getReference();
    }

    public static StorageReference getStorageReference() { return image_database.getReference(); }

}
