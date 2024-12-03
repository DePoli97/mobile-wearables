package com.example.inventorymapper.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymapper.R;
import com.example.inventorymapper.Database;
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
    private LocationAdapter locationAdapter;
    private Button backButton;
    private TextView addNewText, addNewItemText;

    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize Views
        householdsRecyclerView = root.findViewById(R.id.recycler_view);
        itemsRecyclerView = root.findViewById(R.id.items_recycler_view);
        backButton = root.findViewById(R.id.back_to_households_button);
        addNewText = root.findViewById(R.id.add_new_text);
        addNewItemText = root.findViewById(R.id.add_new_item_text);

        // Set up RecyclerViews
        householdsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize ViewModel
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Set up Household Adapter with click listener
        householdAdapter = new HouseholdAdapter(new ArrayList<>(), household -> {
            // Show items for the clicked Household
            displayItemsForHousehold(household);
        });
        householdsRecyclerView.setAdapter(householdAdapter);

        // Set up Location Adapter for items
        locationAdapter = new LocationAdapter(new ArrayList<>());
        itemsRecyclerView.setAdapter(locationAdapter);

        // Observe Household Data
        homeViewModel.getHouseholds().observe(getViewLifecycleOwner(), households -> {
            householdAdapter.setHouseholds(households);
        });

        // Handle "Add New Location" click
        addNewText.setOnClickListener(v -> {
            HouseholdCreationForm form = new HouseholdCreationForm();
            form.show(getParentFragmentManager(), "Household-form");
        });

        // Handle "Add New Item" click
        addNewItemText.setOnClickListener(v -> {
            ItemCreationForm form = new ItemCreationForm();
            form.show(getParentFragmentManager(), "Item-form");
        });

        // Handle "Back to Households" click
        backButton.setOnClickListener(v -> showHouseholdsList());

        return root;
    }

    private void displayItemsForHousehold(Household household) {
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

                        // Show items RecyclerView and "Add New Item" Text, hide households
                        householdsRecyclerView.setVisibility(View.GONE);
                        addNewText.setVisibility(View.GONE);
                        itemsRecyclerView.setVisibility(View.VISIBLE);
                        addNewItemText.setVisibility(View.VISIBLE);
                        backButton.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("HomeFragment", "Database error: " + databaseError.getMessage());
                    }
                });
    }

    private void showHouseholdsList() {
        // Show the Households list and "Add New Location" button
        householdsRecyclerView.setVisibility(View.VISIBLE);
        addNewText.setVisibility(View.VISIBLE);

        // Hide items RecyclerView, "Add New Item" Text, and back button
        itemsRecyclerView.setVisibility(View.GONE);
        addNewItemText.setVisibility(View.GONE);
        backButton.setVisibility(View.GONE);
    }
}
