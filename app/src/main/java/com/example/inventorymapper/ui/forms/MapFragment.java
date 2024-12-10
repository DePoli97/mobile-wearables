package com.example.inventorymapper.ui.forms;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.inventorymapper.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;

public class MapFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        MapView map = root.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        map.setZoomLevel(1.0);
        map.setMinZoomLevel(1.0);

        // limit north-south scrolling
        map.setScrollableAreaLimitLatitude(MapView.getTileSystem().getMaxLatitude(),MapView. getTileSystem().getMinLatitude(),0);

        Log.d("Map", "Map created");

        return root;
    }
}
