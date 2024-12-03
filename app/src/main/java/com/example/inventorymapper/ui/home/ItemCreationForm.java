package com.example.inventorymapper.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.inventorymapper.Database;
import com.example.inventorymapper.R;
import com.example.inventorymapper.ui.model.Household;
import com.example.inventorymapper.ui.model.Location;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemCreationForm extends DialogFragment {
    private TextView textName;
    private TextView textDescription;
    private Button addButton;
    private Spinner householdSpinner;
    private Spinner locationSpinner;
    private ValueEventListener householdListener;
    private ValueEventListener locationListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_item_creation_form, container, false);

        this.textName = root.findViewById(R.id.item_name);
        this.textDescription = root.findViewById(R.id.item_desc);
        this.addButton = root.findViewById(R.id.confirm_btn);

        this.householdSpinner = root.findViewById(R.id.house_spinner);
        ArrayList<Household> house_subjects = new ArrayList<>();
        ArrayAdapter<Household> houseAdapter = new HouseHoldAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, house_subjects);
        this.householdListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren()
                        .forEach((snapshot) -> {
                            Household household = snapshot.getValue(Household.class);
                            household.setId(snapshot.getKey());
                            if(snapshot.getKey() == null) {
                                Log.d("DB", "key is null!");
                            }
                            house_subjects.add(household);
                        });
                houseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(this.getClass().getName(), "getHouseholds cancelled", error.toException());
            }
        };
        houseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Database.getHouseholds().addValueEventListener(householdListener);
        householdSpinner.setAdapter(houseAdapter);

        /*
        this.locationSpinner = root.findViewById(R.id.location_spinner);
        ArrayList<Location> location_subjects = new ArrayList<>();
        ArrayAdapter<Location> locationAdapter = new LocationAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, location_subjects);

        this.locationListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren()
                        .forEach((snapshot) -> {
                            Location location = snapshot.getValue(Location.class);
                            location.setId(snapshot.getKey());
                            if(snapshot.getKey() == null) {
                                Log.d("DB", "key is null!");
                            }
                            location_subjects.add(location);
                        });
                locationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(this.getClass().getName(), "getHouseholds cancelled", error.toException());
            }
        };
        locationSpinner.setAdapter(houseAdapter);
        */

        this.addButton.setOnClickListener(view -> {
            this.add_item();
            this.dismissNow();
        });

        return root;
    }

    private void add_item() {
        String locationName = this.textName.getText().toString();
        String locationDesc = this.textDescription.getText().toString();
        Household household = (Household) this.householdSpinner.getSelectedItem();

        Database.addItem(locationName, locationDesc, household.getId());
    }
}
