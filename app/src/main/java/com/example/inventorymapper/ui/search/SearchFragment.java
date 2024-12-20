package com.example.inventorymapper.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymapper.R;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private RecyclerView itemsRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        SearchView search = root.findViewById(R.id.search);
        searchViewModel = new ViewModelProvider(getActivity()).get(SearchViewModel.class);
        search.setSubmitButtonEnabled(true);
        search.requestFocus();

        itemsRecyclerView = root.findViewById(R.id.items_recycler);
        itemsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        SearchItemAdapter itemsAdapter = new SearchItemAdapter(searchViewModel.getSearchItems().getValue());
        searchViewModel.getSearchItems().observe(getActivity(), itemsAdapter::setItems);
        itemsRecyclerView.setAdapter(itemsAdapter);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Search", query);
                searchViewModel.setItemsBasedOnQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("Search", "New text: " +newText);
                searchViewModel.setItemsBasedOnQuery(newText);
                return true;
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}