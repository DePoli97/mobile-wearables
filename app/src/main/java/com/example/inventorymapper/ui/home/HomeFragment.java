package com.example.inventorymapper.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymapper.LocationViewModel;
import com.example.inventorymapper.R;
import com.example.inventorymapper.Database;
import com.example.inventorymapper.ui.forms.HouseholdCreationForm;
import com.example.inventorymapper.ui.model.Household;
import com.example.inventorymapper.ui.model.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class HomeFragment extends Fragment {

    private RecyclerView householdsRecyclerView, itemsRecyclerView;
    private HouseholdAdapter householdAdapter;
    private ItemAdapter locationAdapter;
    private Button backButton;
    private View addNewHouseholdView;

    private HomeViewModel homeViewModel;
    private LocationViewModel locationViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize Views
        householdsRecyclerView = root.findViewById(R.id.recycler_view);
        itemsRecyclerView = root.findViewById(R.id.items_recycler_view);
        backButton = root.findViewById(R.id.back_to_households_button);

        // Set up RecyclerViews
        householdsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize ViewModel
        homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        locationViewModel = new ViewModelProvider(getActivity()).get(LocationViewModel.class);
        locationViewModel.getLocation().observe(getViewLifecycleOwner(), location -> {
            homeViewModel.sortHouseholdsByLocation(location);
            Log.d("Location", "Setting location from home view");
        });

        // Set up Household Adapter with click listener
        householdAdapter = new HouseholdAdapter(new ArrayList<>(), household -> {
            // Show items for the clicked Household
            displayItemsForHousehold(household);
        });
        householdsRecyclerView.setAdapter(householdAdapter);

        // Set up Location Adapter for items
        locationAdapter = new ItemAdapter(new ArrayList<>());
        itemsRecyclerView.setAdapter(locationAdapter);

        // Observe Household Data
        homeViewModel.getHouseholds().observe(getViewLifecycleOwner(), households -> {
            householdAdapter.setHouseholds(households);
        });

        // ADD NEW HOUSEHOLD BUTTON
        addNewHouseholdView = root.findViewById(R.id.add_new_household);
        // Find the TextView within the included layout
        TextView householdName = addNewHouseholdView.findViewById(R.id.household_name);
        // Set the text to "Add New Household"
        householdName.setText("Add New Household");
        // Optionally, change the icon if desired
        ImageView householdIcon = addNewHouseholdView.findViewById(R.id.household_icon);
        householdIcon.setImageResource(R.drawable.plus);

        // Set the click listener
        addNewHouseholdView.setOnClickListener(v -> {
            HouseholdCreationForm form = new HouseholdCreationForm();
            form.show(getParentFragmentManager(), "Household-form");
        });


        // Handle "Back to Households" click
        backButton.setOnClickListener(v -> showHouseholdsList());

        return root;
    }

    private void displayItemsForHousehold(Household household) {
        // change string menu_home from string.xml
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(household.getName());

        // Show items RecyclerView and "Add New Item" Text, hide households
        householdsRecyclerView.setVisibility(View.GONE);
        addNewHouseholdView.setVisibility(View.GONE);
        itemsRecyclerView.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.VISIBLE);


        // Fetch items for the selected Household
        Database.getAllHouseholds().child(household.getId()).child("location").child("items")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Item> items = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Item item = snapshot.getValue(Item.class);
                            if (item != null) {
                                items.add(item);
                            }
                        }
                        locationAdapter.setItems(items);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("HomeFragment", "Database error: " + databaseError.getMessage());
                    }
                });
    }

    private void showHouseholdsList() {
        // change back
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_home);


        // Show the Households list and "Add New Location" button

        householdsRecyclerView.setVisibility(View.VISIBLE);
        addNewHouseholdView.setVisibility(View.VISIBLE);

        // Hide items RecyclerView, "Add New Item" Text, and back button
        itemsRecyclerView.setVisibility(View.GONE);
        backButton.setVisibility(View.GONE);
    }
}
