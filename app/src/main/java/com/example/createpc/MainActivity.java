package com.example.createpc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SwitchCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import com.example.createpc.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        settings = getSharedPreferences("Settings", MODE_PRIVATE);

        //DarkTheme Switch
        SwitchCompat switchCompat = activityMainBinding.switchTheme;
        //Check theme settings
        if (settings.getBoolean("theme", true)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            switchCompat.setChecked(true);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            switchCompat.setChecked(false);
        }
        //Listener for changing app theme
        switchCompat.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!b) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("theme", false);
                editor.apply();
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("theme", true);
                editor.apply();
            }
        });

        //Language toggle buttons
        MaterialButtonToggleGroup toggleGroup = activityMainBinding.buttonToggleGroup;
        MaterialButton ru_btn = activityMainBinding.ruBtn;
        MaterialButton en_btn = activityMainBinding.enBtn;
        Drawable ic_check = AppCompatResources.getDrawable(this, R.drawable.ic_check_18);
        //Check language settings
        if (settings.getString("language", "Ru").equals("Ru")) {
            ru_btn.setChecked(true);
            ru_btn.setIcon(ic_check);
            ru_btn.setCheckable(false);
        } else {
            en_btn.setChecked(true);
            en_btn.setIcon(ic_check);
            en_btn.setStrokeWidth((int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));
            en_btn.setTextColor(getResources().getColor(R.color.black));
            ru_btn.setStrokeWidth((int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 21, getResources().getDisplayMetrics()));

            en_btn.setCheckable(false);
        }
        //Listener for changing app language
        toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (checkedId == R.id.ru_btn) {
                //TODO: add language changing
                if (isChecked) {
                    ru_btn.setIcon(ic_check);
                    ru_btn.setStrokeWidth((int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));
                    ru_btn.setCheckable(false);
                    en_btn.setCheckable(true);
                    en_btn.setChecked(false);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("language", "Ru");
                    editor.apply();
                }
                else {
                    ru_btn.setIcon(null);
                    ru_btn.setStrokeWidth((int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 21, getResources().getDisplayMetrics()));
                }
            } else if (checkedId == R.id.en_btn) {
                //TODO: add language changing
                if (isChecked) {
                    en_btn.setIcon(ic_check);
                    en_btn.setStrokeWidth((int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));
                    en_btn.setCheckable(false);
                    ru_btn.setCheckable(true);
                    ru_btn.setChecked(false);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("language", "En");
                    editor.apply();
                }
                else {
                    en_btn.setIcon(null);
                    en_btn.setStrokeWidth((int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 21, getResources().getDisplayMetrics()));
                }
            }
        });

        //Bottom Navigation Menu
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNavigationView = activityMainBinding.navigationMenu;

        //Listener for app navigation
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemID = item.getItemId();
            if (itemID == R.id.page_create) {
                if (!Objects.equals(navController.getCurrentDestination(), navController.findDestination(R.id.startFragment))) {
                    item.setChecked(true);
                    navController.navigate(R.id.startFragment);
                }
            } else if (itemID == R.id.page_search) {
                if (!Objects.equals(navController.getCurrentDestination(), navController.findDestination(R.id.searchFragment))) {
                    item.setChecked(true);
                    navController.navigate(R.id.searchFragment);
                }
            } else if (itemID == R.id.page_builds) {
                if (!Objects.equals(navController.getCurrentDestination(), navController.findDestination(R.id.buildsFragment))) {
                    item.setChecked(true);
                    navController.navigate(R.id.buildsFragment);
                }
            }
            return false;
        });
    }
}
