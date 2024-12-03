package com.example.inventorymapper.ui.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.inventorymapper.ui.model.Household;

import java.util.List;

public class SpinnerHouseholdAdapter extends ArrayAdapter<Household> {

    public SpinnerHouseholdAdapter(@NonNull Context context, int ignored_resource, @NonNull List<Household> objects) {
        super(context, android.R.layout.simple_spinner_dropdown_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        Household item = getItem(position);
        view.setText(item.getName());
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        Household item = getItem(position);
        view.setText(item.getName());
        return view;
    }
}
