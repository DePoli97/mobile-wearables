package com.example.inventorymapper.ui.forms;


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
import java.util.List;

public class LocationCreationForm extends DialogFragment {
    private TextView locationName;
    private TextView locationDesc;
    private Button addButton;
    private Spinner householdSpinner;
    private ValueEventListener listener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_location_creation_form, container, false);

        this.locationName = root.findViewById(R.id.location_name);
        this.locationDesc = root.findViewById(R.id.location_desc);
        this.addButton = root.findViewById(R.id.confirm_btn);
        this.householdSpinner = root.findViewById(R.id.spinner);

        List<Household> subjects = new ArrayList<>();

        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, subjects);
        ArrayAdapter<Household> adapter = new SpinnerHouseholdAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, subjects);

        this.listener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren()
                        .forEach((snapshot) -> {
                            Household household = snapshot.getValue(Household.class);
                            household.setId(snapshot.getKey());
                            if(snapshot.getKey() == null) {
                                Log.d("DB", "key is null!");
                            }
                            subjects.add(household);
                        });
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(this.getClass().getName(), "getHouseholds cancelled", error.toException());
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Database.getHouseholds().addValueEventListener(listener);
        this.householdSpinner.setAdapter(adapter);

        this.addButton.setOnClickListener(view -> {
            this.add_location();
            this.dismissNow();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Database.getHouseholds().removeEventListener(this.listener);
    }

    private void add_location() {
        String locationDesc = this.locationDesc.getText().toString();
        String locationName = this.locationName.getText().toString();
        Household household = (Household) this.householdSpinner.getSelectedItem();
        Database.addLocation(new Location(locationName, locationDesc), household.getId());
    }
}
