package com.example.inventorymapper.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymapper.R;
import com.example.inventorymapper.ui.model.Item;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private List<Item> items;

    public LocationAdapter(List<Item> items) {
        this.items = items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Item item = items.get(position);
        holder.nameTextView.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        LocationViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_name);
        }
    }
}
