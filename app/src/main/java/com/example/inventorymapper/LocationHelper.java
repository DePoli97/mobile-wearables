package com.example.inventorymapper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.Manifest;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.List;
import java.util.Optional;

public final class LocationHelper  {
    public static final int REQUEST_LOCATION_PERMISSION = 12345;
    public static final int REQUEST_LOCATION_FLUSH = 5314;
    private static LocationManager locationManager;
    private static LocationListener locationListener;
    private static String provider;
    private static Location location;

    @SuppressLint("MissingPermission")
    public static Optional<Location> getCurrentLocation() {
        if (provider == null) {
            Log.e("Location", "NULL provider!");
            return Optional.empty();
        }

        if(locationManager == null) {
            Log.d("Location", "Location not set up");
            return Optional.empty();
        }

        setLocation(locationManager.getLastKnownLocation(provider));

        if(location == null) {

            return Optional.empty();
        }

        return Optional.of(location);
    }

    public static Location getDummyLocation() {
        Location dummyLocation = new Location(provider);
        dummyLocation.setLatitude(51);
        return dummyLocation;
    }

    private static void setLocation(Location newLocation) {
        if (location == null) {
            return;
        }
        location = newLocation;
        Log.d("Location", "Update: Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude());
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
     * Asks user for permission to use location services.
     * @param act current application activity
     * @param ctx current application context
     */
    @SuppressLint("MissingPermission") // location permission is checked through `has_location_permissions`
    public static void getLocationPermission(Activity act, Context ctx) {
        if (!has_location_permissions(ctx)) {
            Log.d("Location", "Requesting permission");
            ActivityCompat.requestPermissions(act,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, REQUEST_LOCATION_PERMISSION);
            return;
        }

        locationListener = new LocationListener() {
            @Override
            public void onFlushComplete(int requestCode) {
                LocationListener.super.onFlushComplete(requestCode);
                Log.d("Location", "Flush complete");
            }

            @Override
            public void onLocationChanged(@NonNull List<Location> locations) {
                LocationListener.super.onLocationChanged(locations);

                // select last location
                for (Location loc:  locations) {
                    setLocation(loc);
                }
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                LocationListener.super.onProviderEnabled(provider);
                // TODO: handle various providers
                switch (provider) {
                    case LocationManager.GPS_PROVIDER:
                        setProvider(LocationManager.GPS_PROVIDER);
                        break;
                }
                getCurrentLocation();

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                LocationListener.super.onProviderDisabled(provider);
                // TODO: handle various providers
                switch (provider) {
                    case LocationManager.GPS_PROVIDER:
                        setProvider(LocationManager.NETWORK_PROVIDER);
                        break;
                }
                getCurrentLocation();
            }

            @Override
            public void onLocationChanged(@NonNull Location location) {
                setLocation(location);
            }
        };

        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);

        if (!setProvider(LocationManager.GPS_PROVIDER)
            && !setProvider(LocationManager.NETWORK_PROVIDER)
                && !setProvider(LocationManager.PASSIVE_PROVIDER)) {
            Log.e("Location", "Unable to set location");
        }
        Log.d("Location", String.format("Chosen provider: %s", provider));
        setLocation(locationManager.getLastKnownLocation(provider));
    }

    public static String getProvider() {
        return provider;
    }

    @SuppressLint("MissingPermission") /* called only within a permission OK context */
    private static boolean setProvider(String new_provider) {
        if (!locationManager.isProviderEnabled(new_provider)) {
            Log.e("Location", String.format("new provider \"%s\" is disabled", new_provider));
            return false;
        }

        locationManager.removeUpdates(locationListener);
        locationManager.requestLocationUpdates(new_provider, 1000, 1, locationListener);
        provider = new_provider;

        Log.d("Location", String.format("Provider: %s set!", provider));
        return true;
    }
}
