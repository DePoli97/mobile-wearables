package com.example.inventorymapper;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.example.inventorymapper.ui.home.HouseholdCreationForm;
import com.example.inventorymapper.ui.home.LocationCreationForm;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorymapper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    static boolean tmp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                if(tmp) {
                    HouseholdCreationForm form = new HouseholdCreationForm();
                    form.show(getSupportFragmentManager(), "Household-form");
                } else {
                    LocationCreationForm form = new LocationCreationForm();
                    form.show(getSupportFragmentManager(), "Location-form");
                }
                */
                ItemCreationForm form = new ItemCreationForm();
                form.show(getSupportFragmentManager(), "Item-form");
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_households, R.id.nav_search, R.id.nav_user)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Example: Add a new item
        //Database.addItem("Chair", "A comfortable chair");
        //Item item = new Item("Table", "A sturdy table");
        //Database.addItem(item);
    }

    public FloatingActionButton getActionButton() {
        return binding.appBarMain.fab;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}