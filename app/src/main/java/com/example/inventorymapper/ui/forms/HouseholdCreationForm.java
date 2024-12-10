package com.example.inventorymapper.ui.forms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentContainerView;

import com.example.inventorymapper.Database;
import com.example.inventorymapper.LocationHelper;
import com.example.inventorymapper.R;
import com.example.inventorymapper.ui.model.Household;
import com.example.inventorymapper.ui.model.Location;

import java.util.List;
import java.util.Optional;

public class HouseholdCreationForm extends DialogFragment {
    private TextView textName;
    private TextView locationName;
    private TextView locationDesc;
    private Button addButton;
    private FragmentContainerView mapContainerView;
    private android.location.Location location;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_household_creation_form, container, false);

        this.textName = root.findViewById(R.id.house_name);
        this.locationDesc = root.findViewById(R.id.location_desc);
        this.mapContainerView = root.findViewById(R.id.mapContainer);
        mapContainerView.setVisibility(View.GONE);
        MapFragment map = mapContainerView.getFragment();

        this.addButton = root.findViewById(R.id.confirm_btn);
        this.addButton.setOnClickListener(view -> {
            this.add_household();
            this.dismissNow();
        });
        Optional<android.location.Location> loc = LocationHelper.getCurrentLocation();
        if (false && loc.isPresent()) {
            location = loc.get();
        } else {
            mapContainerView.setVisibility(View.VISIBLE);
        }
        return root;
    }

    private void add_household() {
        String name = this.textName.getText().toString();
        String locationDesc = this.locationDesc.getText().toString();
        //android.location.Location location;

        Optional<android.location.Location> optLoc = LocationHelper.getCurrentLocation();
        if (false && optLoc.isPresent()) {
            location = optLoc.get();
            Household household = new Household(null, name, new Location(name, locationDesc), List.of(), location);
            Database.addHousehold(household);
        } else {
            Toast.makeText(getContext(), "Unable to get location, aborting", Toast.LENGTH_LONG).show();
        }

        // Create the Household object with the generated ID


        // Save the object in the database
        /*
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
         */
    }

}
