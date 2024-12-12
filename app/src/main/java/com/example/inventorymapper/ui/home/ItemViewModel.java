package com.example.inventorymapper.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inventorymapper.ui.model.Item;

public class ItemViewModel extends ViewModel {

    private final MutableLiveData<Item> itemLiveData = new MutableLiveData<>();

    public void setItem(Item item) {
        itemLiveData.setValue(item);
    }

    public MutableLiveData<Item> getItemLiveData() {
        return itemLiveData;
    }
}
