package com.example.inventorymapper.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inventorymapper.LocationHelper;
import com.example.inventorymapper.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private LocationHelper locationHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // location
        if(!LocationHelper.has_location_permissions(getContext())) {
            LocationHelper.getLocationPermission(getActivity(), getContext());
        }

        if (LocationHelper.has_location_permissions(getContext())) {
            this.locationHelper = new LocationHelper(getContext());
        } else {
            Toast.makeText(getContext(), "Unable to use location service", Toast.LENGTH_SHORT).show();
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}