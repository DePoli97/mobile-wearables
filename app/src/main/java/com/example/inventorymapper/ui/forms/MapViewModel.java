package com.example.inventorymapper.ui.forms;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inventorymapper.LocationHelper;

public class MapViewModel extends ViewModel {
    private final MutableLiveData<Location> location = new MutableLiveData<>();

    public void setLocation(Location location) {
        this.location.setValue(location);
    }

    public void setLocation(double latitude, double longitude) {
        Location loc = this.location.getValue();
        if (loc == null) {
           loc = LocationHelper.getDummyLocation();
        }
        loc.setLatitude(latitude);
        loc.setLongitude(longitude);
        this.location.setValue(loc);
    }

    @NonNull
    public LiveData<Location> getLocation() {
        return this.location;
    }
}
