package com.example.inventorymapper.ui.home;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inventorymapper.Database;
import com.example.inventorymapper.LocationHelper;
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
            Log.d("Home", "Loading households from db");
            loadHouseholds();
        }

        return householdsLiveData;
    }

    public void sortHouseholdsByLocation(Location location) {
        List<Household> households = householdsLiveData.getValue();

        if (households == null) {
            Log.d("Home", "Null households to be sorted");
            return;
        }

        households.sort((o1, o2) -> {
//            float[] distance1 = {0};
//            float[] distance2 = {0};
//            Location.distanceBetween(location.getLatitude(), location.getLongitude(), o1.getLatitude(), o1.getLongitude(), distance1);
//            Location.distanceBetween(location.getLatitude(), location.getLongitude(), o2.getLatitude(), o2.getLongitude(), distance2);
//
//            if (Math.abs(distance1[0] - distance2[0]) < LocationHelper.COMPARE_DISTANCE_THRESHOLD) {
//                return 0;
//            }
//
//            if (distance1[0] < distance2[0]) {
//                return -1;
//            } else if (distance1[0] > distance2[0]) {
//                return 1;
//            }
            return 0;
        });
        Log.d("Location", "Households sorted by location");
        householdsLiveData.setValue(households);
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
                sortHouseholdsByLocation(LocationHelper.getCurrentLocation().getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("HomeViewModel", "Database Error: " + databaseError.getMessage());
            }
        });
    }
}