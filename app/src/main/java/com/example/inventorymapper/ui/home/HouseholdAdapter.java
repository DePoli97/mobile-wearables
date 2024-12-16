package com.example.inventorymapper.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymapper.R;
import com.example.inventorymapper.ui.model.Household;

import java.util.List;

public class HouseholdAdapter extends RecyclerView.Adapter<HouseholdAdapter.HouseholdViewHolder> {

    private List<Household> households;
    private OnHouseholdClickListener clickListener;

    public HouseholdAdapter(List<Household> households, OnHouseholdClickListener clickListener) {
        this.households = households;
        this.clickListener = clickListener;
    }

    public void setHouseholds(List<Household> households) {
        this.households = households;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HouseholdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_household, parent, false);
        return new HouseholdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseholdViewHolder holder, int position) {
        Household household = households.get(position);
        holder.nameTextView.setText(household.getName());

        // Handle click event
        holder.itemView.setOnClickListener(v -> clickListener.onHouseholdClick(household));
    }

    @Override
    public int getItemCount() {
        return households != null ? households.size() : 0;
    }

    static class HouseholdViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        HouseholdViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_name);
        }
    }

    public interface OnHouseholdClickListener {
        void onHouseholdClick(Household household);
    }
}
