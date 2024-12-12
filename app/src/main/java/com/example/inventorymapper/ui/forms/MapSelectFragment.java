package com.example.inventorymapper.ui.forms;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inventorymapper.LocationViewModel;
import com.example.inventorymapper.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.api.IMapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

public class MapSelectFragment extends Fragment {
    private MapViewModel data;
    private LocationViewModel locationData;
    private MapView map;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        this.data = new ViewModelProvider(getActivity()).get(MapViewModel.class);
        this.locationData = new ViewModelProvider((getActivity())).get(LocationViewModel.class);

        if (locationData.getLocation().getValue() != null) {
            this.data.setLocation(locationData.getLocation().getValue());
            this.locationData.getLocation().observe(getActivity(), location -> {
                this.data.setLocation(locationData.getLocation().getValue());
            });
        }

        Location loc = data.getLocation().getValue();
        GeoPoint currentPos = new GeoPoint(loc.getLatitude(), loc.getLongitude());

        map = root.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        IMapController controller = map.getController();
        controller.setCenter(currentPos);
        controller.setZoom(12.0);

        Marker marker = new Marker(map);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setDefaultIcon();
        marker.setDraggable(true);
        marker.setPosition(currentPos);
        map.getOverlays().add(marker);

        MapEventsOverlay eventsOverlay = new MapEventsOverlay(new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                marker.setPosition(p);
                data.setLocation(p.getLatitude(), p.getLongitude());
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {

                return false;
            }
        });
        map.getOverlays().add(eventsOverlay);

        marker.setOnMarkerDragListener(new Marker.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(Marker marker) {
                GeoPoint pos = marker.getPosition();
                data.setLocation(pos.getLatitude(), pos.getLongitude());
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

            }

            @Override
            public void onMarkerDragStart(Marker marker) {

            }
        });

        map.setMultiTouchControls(true);
        map.setMinZoomLevel(1.2);

        // limit north-south scrolling
        map.setScrollableAreaLimitLatitude(MapView.getTileSystem().getMaxLatitude(),MapView. getTileSystem().getMinLatitude(),0);

        Log.d("Map", "Map created");

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
