package com.example.inventorymapper.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymapper.Database;
import com.example.inventorymapper.R;
import com.example.inventorymapper.ui.model.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ItemFragment extends Fragment {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_location, container, false);

        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ItemAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Get arguments passed from HomeFragment
        String householdId = getArguments().getString("householdId");

        // Fetch data from the database
        Database.getAllHouseholds().child(householdId).child("location").child("items")
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
                        adapter.setItems(items);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("LocationFragment", "Database error: " + databaseError.getMessage());
                    }
                });

        return root;
    }
}
