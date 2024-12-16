package com.example.inventorymapper.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymapper.R;
import com.example.inventorymapper.ui.model.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.LocationViewHolder> {

    private List<Item> items;

    public ItemAdapter(List<Item> items) {
        this.items = items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false);
        return new LocationViewHolder(view);
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView imageView;
    
        LocationViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_name);
            imageView = itemView.findViewById(R.id.item_image);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Item item = items.get(position);
        holder.nameTextView.setText(item.getName());
        // Bind the image (replace with your image resource or loading logic)
        holder.imageView.setImageResource(R.drawable.plus);
        // Set the click listener
        holder.itemView.setOnClickListener(v -> {
            // Open ItemDetails dialog
            ItemDetails itemDetails = new ItemDetails();
            ItemViewModel itemViewModel = new ViewModelProvider((FragmentActivity) v.getContext()).get(ItemViewModel.class);
            itemViewModel.setItem(item);
            itemDetails.show(((FragmentActivity) v.getContext()).getSupportFragmentManager(), "ItemDetails");
        });
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }
}
