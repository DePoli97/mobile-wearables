package com.example.inventorymapper.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.inventorymapper.Database;
import com.example.inventorymapper.R;

public class ItemCreationForm extends DialogFragment {
    private TextView textName;
    private TextView textDescription;
    private Button addButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_item_creation_form, container, false);

        this.textName = root.findViewById(R.id.name_input);
        this.textDescription = root.findViewById(R.id.desc_input);
        this.addButton = root.findViewById(R.id.confirm_btn);
        this.addButton.setOnClickListener(view -> {
            this.add_item();
            this.dismissNow();
        });

        return root;
    }

    private void add_item() {
        Database.addItem(this.textName.getText().toString(), this.textDescription.getText().toString());
    }
}
