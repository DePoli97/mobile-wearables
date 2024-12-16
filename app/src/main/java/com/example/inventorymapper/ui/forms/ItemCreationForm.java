package com.example.inventorymapper.ui.forms;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inventorymapper.Database;
import com.example.inventorymapper.LocationHelper;
import android.Manifest;
import com.example.inventorymapper.R;
import com.example.inventorymapper.Storage;
import com.example.inventorymapper.ui.home.HomeViewModel;
import com.example.inventorymapper.ui.model.Household;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.widget.ImageView;


import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

public class ItemCreationForm extends DialogFragment {
    private static final int CAMERA_REQUEST_CODE = 100;
    private TextView textName;
    private TextView textDescription;
    private String photoUri;
    private ActivityResultLauncher<Intent> takePictureLauncher;
    private ImageView previewImageView; // To show a preview of the captured image
    private Bitmap capturedImage;

    private Button addButton;
    private Spinner householdSpinner;
    private HomeViewModel homeViewModel;
    private List<Household> house_subjects;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_item_creation_form, container, false);

        this.textName = root.findViewById(R.id.item_name);
        this.textDescription = root.findViewById(R.id.item_desc);
        this.addButton = root.findViewById(R.id.confirm_btn);

        this.homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        this.householdSpinner = root.findViewById(R.id.house_spinner);
        android.location.Location loc = LocationHelper.getCurrentLocation().getValue();
        if (loc == null) loc = LocationHelper.getDummyLocation();
        homeViewModel.sortHouseholdsByLocation(loc);
        house_subjects = homeViewModel.getHouseholds().getValue();
        if (house_subjects == null) {
            house_subjects = List.of();
            Log.e("Item", "Null list of households");
        }
        ArrayAdapter<Household> houseAdapter = new SpinnerHouseholdAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, house_subjects);

        homeViewModel.getHouseholds().observe(getActivity(), households -> {
            house_subjects = households;
            houseAdapter.notifyDataSetChanged();
        });

        houseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        householdSpinner.setAdapter(houseAdapter);

        this.addButton.setOnClickListener(view -> {
            String locationName = this.textName.getText().toString();
            String locationDesc = this.textDescription.getText().toString();
            Household household = (Household) this.householdSpinner.getSelectedItem();
            if(household == null) {
                Toast.makeText(getContext(), "No household selected", Toast.LENGTH_SHORT).show();
                return;
            }

            Database.addItem(locationName, locationDesc, photoUri, household.getId());
            this.dismissNow();
        });

        //////////////////////////////////
        // OPEN CAMERA AND TAKE PICTURE //
        //////////////////////////////////

        // Initialize the launcher for camera intent
        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                        Bundle extras = result.getData().getExtras();
                        capturedImage = (Bitmap) extras.get("data");
                        if (capturedImage != null) {
                            // convert the image to a byte array
                            byte[] imageData = convertBitmapToByteArray(capturedImage);

                            // Crop the image to a square
                            capturedImage = cropToSquare(capturedImage);

                            // Generate a unique file name for the image
                            String format = ".jpg";
                            String uniqueFileName = UUID.randomUUID().toString() + format;
                            photoUri = uniqueFileName;

                            // Save the image in Storage
                            Storage.uploadImage(imageData, "images/" + uniqueFileName, task -> {
                                if (task.isSuccessful()) {
                                    // Get the download URL of the image
                                    Storage.getDownloadUrl("images/" + uniqueFileName,
                                            uri -> {
                                                // Do something with the download URL
                                                Log.d("ItemCreationForm", "Download URL: " + uri.toString());
                                            },
                                            e -> {
                                                // Handle error
                                                Log.e("ItemCreationForm", "Failed to get download URL", e);
                                            });
                                } else {
                                    // Handle error
                                    Log.e("ItemCreationForm", "Failed to upload image", task.getException());
                                }
                            });
                        }
                    } else {
                        Toast.makeText(getContext(), "Failed to capture image", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Open the camera immediately
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        } else {
            openCamera();
        }


        return root;
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            takePictureLauncher.launch(intent);
        } else {
            Toast.makeText(getContext(), "Camera not available", Toast.LENGTH_SHORT).show();
        }
    }

    public Bitmap getCapturedImage() {
        return capturedImage;
    }

    public byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    private Bitmap cropToSquare(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newEdge = Math.min(width, height);
        int x = (width - newEdge) / 2;
        int y = (height - newEdge) / 2;
        return Bitmap.createBitmap(bitmap, x, y, newEdge, newEdge);
    }

}
