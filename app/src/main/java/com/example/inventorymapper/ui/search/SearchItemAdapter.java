package com.example.inventorymapper.ui.search;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inventorymapper.R;
import com.example.inventorymapper.Storage;
import com.example.inventorymapper.ui.home.ItemDetails;
import com.example.inventorymapper.ui.home.ItemViewModel;
import com.example.inventorymapper.ui.model.Item;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.LocationViewHolder> {

    private List<Item> items;

    public SearchItemAdapter(List<Item> items) {
        this.items = items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new LocationViewHolder(view);
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView houseName;
        TextView nameTextView;
        ImageView imageView;
    
        LocationViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_name);
            houseName = itemView.findViewById(R.id.house_name);
            imageView = itemView.findViewById(R.id.item_image);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Item item = items.get(position);
        holder.nameTextView.setText(item.getName());
        holder.houseName.setText(item.getId());
        Log.d("Search", "Item household name: " + item.getId());
        // Bind the image (replace with your image resource or loading logic)
        Storage.getDownloadUrl("images/" + item.getPhotoUri(), new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView)
                        .load(uri)
                        .placeholder(R.drawable.item)
                        .into(holder.imageView);
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Log the error
                Log.e("ItemAdapter", "Failed to load image: " + e.getMessage());
                // Handle any errors
                holder.imageView.setImageResource(R.drawable.item);
            }
        });

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
