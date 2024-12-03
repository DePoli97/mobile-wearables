package com.example.inventorymapper.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymapper.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private HouseholdAdapter adapter;
    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize RecyclerView
        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HouseholdAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Add click listener to "Add new location" TextView
        TextView addNewText = root.findViewById(R.id.add_new_text);
        addNewText.setOnClickListener(v -> {
            // Show the form to create a new household
            HouseholdCreationForm form = new HouseholdCreationForm();
            form.show(getParentFragmentManager(), "Household-form");
        });

        // Observe data from ViewModel and update RecyclerView
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getHouseholds().observe(getViewLifecycleOwner(), households -> {
            adapter.setHouseholds(households);
        });

        return root;
    }
}