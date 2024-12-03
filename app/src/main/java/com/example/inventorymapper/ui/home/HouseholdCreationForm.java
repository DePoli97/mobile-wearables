package com.example.inventorymapper.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.inventorymapper.Database;
import com.example.inventorymapper.LocationHelper;
import com.example.inventorymapper.R;
import com.example.inventorymapper.ui.model.Household;
import com.example.inventorymapper.ui.model.Location;
import com.google.firebase.database.DatabaseReference;

import java.util.List;
import java.util.Optional;

public class HouseholdCreationForm extends DialogFragment {
    private TextView textName;
    private TextView locationName;
    private TextView locationDesc;
    private Button addButton;
    private LocationHelper locationHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_household_creation_form, container, false);

        this.textName = root.findViewById(R.id.item_name);
        this.locationName = root.findViewById(R.id.location_name);
        this.locationDesc = root.findViewById(R.id.location_desc);
        this.addButton = root.findViewById(R.id.confirm_btn);
        this.addButton.setOnClickListener(view -> {
            this.add_household();
            this.dismissNow();
        });
        this.locationHelper = new LocationHelper(getContext());

        return root;
    }

    private void add_household() {
        String name = this.textName.getText().toString();
        String locationDesc = this.locationDesc.getText().toString();
        String locationName = this.locationName.getText().toString();
        android.location.Location location;

        Optional<android.location.Location> optLoc = locationHelper.getCurrentLocation();
        if (optLoc.isPresent()) {
            location = optLoc.get();
        } else {
//            Toast.makeText(getContext(), "Unable to get location, aborting", Toast.LENGTH_LONG).show();
            // Create a dummy location
            location = new android.location.Location("");
            location.setLatitude(0.0);
            location.setLongitude(0.0);
        }

        // Generate a unique ID from Firebase
        DatabaseReference ref = Database.getAllHouseholds().push();
        String id = ref.getKey(); // Get the generated key

        // Create the Household object with the generated ID
        Household household = new Household(id, name, new Location(locationName, locationDesc), List.of(), location);

        // Save the object in the database
        ref.setValue(household).addOnCompleteListener(task -> {
            // Check if the fragment is still attached before calling requireContext()
            if (isAdded()) {
                if (task.isSuccessful()) {
                    Toast.makeText(requireContext(), "Household added successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Failed to add household!", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Log if the fragment is detached
                Log.w("HouseholdCreationForm", "Fragment is not attached to context. Skipping Toast.");
            }
        });
    }

}
