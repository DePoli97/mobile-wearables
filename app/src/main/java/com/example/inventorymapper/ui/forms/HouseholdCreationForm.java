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
import androidx.lifecycle.ViewModelProvider;

import com.example.inventorymapper.Database;
import com.example.inventorymapper.R;
import com.example.inventorymapper.ui.model.Household;
import com.example.inventorymapper.ui.model.Location;

import java.util.List;

public class HouseholdCreationForm extends DialogFragment {
    private TextView textName;
    private TextView locationDesc;
    private Button addButton;
    private FragmentContainerView mapContainerView;
    private android.location.Location location;
    private MapViewModel mapViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_household_creation_form, container, false);

        this.textName = root.findViewById(R.id.item_name);
        this.locationDesc = root.findViewById(R.id.location_desc);
        this.mapViewModel = new ViewModelProvider(getActivity()).get(MapViewModel.class);
        this.mapContainerView = root.findViewById(R.id.mapContainer);
        MapSelectFragment map = mapContainerView.getFragment();

        this.addButton = root.findViewById(R.id.confirm_btn);
        this.addButton.setOnClickListener(view -> {
            String name = this.textName.getText().toString();
            String locationDesc = this.locationDesc.getText().toString();

            if (name.isEmpty() || name.isBlank()) {
                this.textName.requestFocus();
                return;
            }

            if (locationDesc.isEmpty() || locationDesc.isBlank()) {
                this.locationDesc.requestFocus();
                return;
            }

            if (mapViewModel.getLocation().getValue() == null) {
                Toast.makeText(getContext(), "Unable to get location, aborting", Toast.LENGTH_LONG).show();
            } else {
                Household household = new Household(null, name, new Location(name, locationDesc), List.of(), location);
                Database.addHousehold(household);
            }
            this.dismissNow();
        });

        mapViewModel.getLocation().observe(this, loc -> {
            this.location = loc;
        });
        return root;
    }
}
