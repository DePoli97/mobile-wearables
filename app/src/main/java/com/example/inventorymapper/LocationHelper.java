package com.example.inventorymapper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.Manifest;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.List;
import java.util.Optional;

public final class LocationHelper implements LocationListener{
    private LocationManager locationManager = null;
    private Location location;

    public LocationHelper(Context context) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
        ) {
            this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
        } else {
            Log.d("Location", "Location: None?");
        }
    }

    public Optional<Location> getCurrentLocation() {
        if(this.locationManager == null) {
            return Optional.empty();
        }

        /*
        * Not sure which is the alternative
        * TODO: find alternative for previous versions
        */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            this.locationManager.requestFlush(LocationManager.GPS_PROVIDER, this, 1001);
        }
        return Optional.of(location);
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
        for (Location loc:  locations) {
            location = loc;
        }
        Log.d("Location", "Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude());
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    /**
     *
     * @param context current application context
     * @return has access to the required location permissions
     */
    public static boolean has_location_permissions(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     *
     * @param act current application activity
     * @param ctx current application context
     * @return has access to the required location permissions
     */
    public static boolean getLocationPermission (Activity act, Context ctx) {
        if (!has_location_permissions(ctx)) {
            ActivityCompat.requestPermissions(act,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    }, 1001);
        }
        return has_location_permissions(ctx);
    }
}
