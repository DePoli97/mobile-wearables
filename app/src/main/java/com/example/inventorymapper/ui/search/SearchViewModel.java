package com.example.inventorymapper.ui.search;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inventorymapper.Database;
import com.example.inventorymapper.ui.model.Household;
import com.example.inventorymapper.ui.model.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SearchViewModel extends ViewModel {

    private final MutableLiveData<List<Item>> items;
    private final MutableLiveData<List<Item>> searchItems;

    public SearchViewModel() {
        items = new MutableLiveData<>();
        items.setValue(new ArrayList<>());

        searchItems = new MutableLiveData<>();
        searchItems.setValue(new ArrayList<>());

        Database.getAllHouseholds().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot househeholdsSnapshot) {
                for (DataSnapshot snapshot : househeholdsSnapshot.getChildren()) {
                    Household household = snapshot.getValue(Household.class);
                    Log.d("Search", household.getName());
                    List<Item> _items = household.getLocation().getItems();
                    if (_items != null) {
                        _items.stream().peek(item -> item.setId(household.getName()));
                        List<Item> localItems = items.getValue();
                        localItems.addAll(_items);
                        items.setValue(localItems);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setItemsBasedOnQuery(String query) {
        List<String> queryTokens = Arrays.stream(query.split(" "))
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        List<Item> items = this.items.getValue();
        if (items != null) {
            List<Item> _searchItems = items.stream()
                    .filter(item -> {
                        List<String> item_tokens = Arrays.stream(item.getName().split(" "))
                                .map(String::toLowerCase)
                                .collect(Collectors.toList());
                        item_tokens.addAll(Arrays.stream(item.getName().split(" "))
                                .map(String::toLowerCase)
                                .collect(Collectors.toList()));

                        return true || item_tokens.stream().anyMatch(token -> queryTokens.stream()
                                .anyMatch(_query -> _query.equals(token)));
                    })
                    .collect(Collectors.toList());
            searchItems.setValue(_searchItems);
        } else {
            Log.e("Search", "NO items");
        }

    }

    public LiveData<List<Item>> getSearchItems() {
        return searchItems;
    }
}