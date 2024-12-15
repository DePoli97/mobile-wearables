package com.example.inventorymapper.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.inventorymapper.R;
import com.example.inventorymapper.Storage;
import com.example.inventorymapper.ui.model.Item;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class ItemDetails extends DialogFragment {
    private Item item;
    private TextView itemName;
    private TextView itemDescription;
    private ImageView itemImage;
    private Button deleteButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.item_details, container, false);

        itemName = root.findViewById(R.id.item_title_TextView);
        itemDescription = root.findViewById(R.id.item_description_TextView);
        itemImage = root.findViewById(R.id.item_image);
        deleteButton = root.findViewById(R.id.item_delete_button);

        ItemViewModel itemViewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        itemViewModel.getItemLiveData().observe(getViewLifecycleOwner(), item -> {
            this.item = item;
            itemName.setText(item.getName());
            itemDescription.setText(item.getDescription());
            loadImage(item.getPhotoUri());
        });
        return root;
    }

    private void loadImage(String photoUri) {
        // log the uri
        Log.d("ItemDetails", "Loading image from: " + photoUri);
        Storage.getDownloadUrl("images/" + photoUri, new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(ItemDetails.this)
                        .load(uri)
//                        .placeholder(R.drawable.mapic_person)
                        .into(itemImage);
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Log the error
                Log.e("ItemDetails", "Failed to load image: " + e.getMessage());
                // Handle any errors
                itemImage.setImageResource(R.drawable.item);
            }
        });
    }
}