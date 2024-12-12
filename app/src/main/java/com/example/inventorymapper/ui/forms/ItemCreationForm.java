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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.inventorymapper.Database;
import com.example.inventorymapper.LocationHelper;
import com.example.inventorymapper.R;
import com.example.inventorymapper.ui.home.HomeViewModel;
import com.example.inventorymapper.ui.model.Household;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ItemCreationForm extends DialogFragment {
    private TextView textName;
    private TextView textDescription;
    private Button addButton;
    private Spinner householdSpinner;
    private HomeViewModel homeViewModel;
    private List<Household> house_subjects;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_item_creation_form, container, false);

        this.textName = root.findViewById(R.id.house_name);
        this.textDescription = root.findViewById(R.id.item_desc);
        this.addButton = root.findViewById(R.id.confirm_btn);

        this.homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        this.householdSpinner = root.findViewById(R.id.house_spinner);
        android.location.Location loc = LocationHelper.getCurrentLocation().getValue();
        if (loc == null) loc = LocationHelper.getDummyLocation();
        homeViewModel.sortHouseholdsByLocation(loc);
        house_subjects = homeViewModel.getHouseholds().getValue();
        if (house_subjects == null) {
            house_subjects = List.of();
            Log.e("Item", "Null list of households");
        }
        ArrayAdapter<Household> houseAdapter = new SpinnerHouseholdAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, house_subjects);

        homeViewModel.getHouseholds().observe(getActivity(), households -> {
            house_subjects = households;
            houseAdapter.notifyDataSetChanged();
        });

        houseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
            String locationName = this.textName.getText().toString();
            String locationDesc = this.textDescription.getText().toString();
            Household household = (Household) this.householdSpinner.getSelectedItem();
            if(household == null) {
                Toast.makeText(getContext(), "No household selected", Toast.LENGTH_SHORT).show();
                return;
            }

            Database.addItem(locationName, locationDesc, household.getId());
            this.dismissNow();
        });

        return root;
    }

}
