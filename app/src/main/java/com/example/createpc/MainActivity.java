package com.example.createpc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.createpc.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    SharedPreferences themeSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        themeSettings = getSharedPreferences("ThemeSettings", MODE_PRIVATE);

        //DarkTheme Switch
        SwitchCompat switchCompat = activityMainBinding.switchTheme;
        if (themeSettings.getBoolean("theme", true)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            switchCompat.setChecked(true);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            switchCompat.setChecked(false);
        }
        switchCompat.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!b) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                SharedPreferences.Editor editor = themeSettings.edit();
                editor.putBoolean("theme", false);
                editor.apply();
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                SharedPreferences.Editor editor = themeSettings.edit();
                editor.putBoolean("theme", true);
                editor.apply();
            }
        });

        //Bottom Navigation Menu
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNavigationView = activityMainBinding.navigationMenu;
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.page_create:
                    item.setChecked(true);
                    navController.navigate(R.id.startFragment);
                    break;
                case R.id.page_search:
                    item.setChecked(true);
                    navController.navigate(R.id.searchFragment);
                    break;
                case R.id.page_builds:
                    item.setChecked(true);
                    navController.navigate(R.id.buildsFragment);
                    break;
            }
            return false;
        });
    }
}