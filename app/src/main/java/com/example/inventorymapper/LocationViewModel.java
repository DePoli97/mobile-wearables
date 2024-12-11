package com.example.inventorymapper;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocationViewModel extends ViewModel {
    private MutableLiveData<Location> location = new MutableLiveData<>();

    public LiveData<Location> getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location.setValue(location);
    }
}

/*
List<Household> households = householdsLiveData.getValue();
        Optional<Location> loc = LocationHelper.getCurrentLocation();
        if (loc.isPresent()) {

            Log.d("Location", "Sorting households");
            Location location = loc.get();
            households.sort((o1, o2) -> {
                float[] distance1 = {0};
                float[] distance2 = {0};
                Location.distanceBetween(location.getLatitude(), location.getLongitude(), o1.getLatitude(), o1.getLongitude(), distance1);
                Location.distanceBetween(location.getLatitude(), location.getLongitude(), o2.getLatitude(), o2.getLongitude(), distance2);

                if (Math.abs(distance1[0] - distance2[0]) < LocationHelper.COMPARE_DISTANCE_THRESHOLD) {
                    return 0;
                }

                if (distance1[0] < distance2[0]) {
                    return 1;
                } else if (distance1[0] > distance2[0]) {
                    return -1;
                }
                return 0;
            });
            householdsLiveData.setValue(households);
        } else {
            Log.d("Location", "Current location not present");
        }
 */
