package com.example.inventorymapper.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inventorymapper.R;
import com.example.inventorymapper.ui.model.Item;

public class ItemDetails extends DialogFragment {
    private Item item;
    private ImageView itemImage;
    private TextView itemName;
    private TextView itemDescription;
    private Button deleteButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.item_details, container, false);

        itemImage = root.findViewById(R.id.item_image);
        itemName = root.findViewById(R.id.item_title_TextView);
        itemDescription = root.findViewById(R.id.item_description_TextView);
        deleteButton = root.findViewById(R.id.item_delete_button);

        ItemViewModel itemViewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        itemViewModel.getItemLiveData().observe(getViewLifecycleOwner(), item -> {
            this.item = item;
            itemImage.setImageResource(R.drawable.mapic_person);
            itemName.setText(item.getName());
            itemDescription.setText(item.getDescription());
        });
        return root;
    }
}
