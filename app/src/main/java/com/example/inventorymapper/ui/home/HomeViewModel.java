package com.example.inventorymapper.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inventorymapper.Database;
import com.example.inventorymapper.ui.model.Household;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<List<Household>> householdsLiveData = new MutableLiveData<>();

    public LiveData<List<Household>> getHouseholds() {
        if (householdsLiveData.getValue() == null) {
            loadHouseholds();
        }
        return householdsLiveData;
    }

    private void loadHouseholds() {
        Database.getAllHouseholds().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("asdf", HomeViewModel.class.getName() + " - Data Changed");
                List<Household> households = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Household household = snapshot.getValue(Household.class);
                    if (household != null) {
                        households.add(household);
                    }
                }
                householdsLiveData.setValue(households); // Notify observers
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("HomeViewModel", "Database Error: " + databaseError.getMessage());
            }
        });
    }
}