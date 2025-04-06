package com.example.sonolite_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DietOptionsActivity extends AppCompatActivity {

    private Button generalDietButton, personalizedDietButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet_options);

        // Initialize buttons
        generalDietButton = findViewById(R.id.general_diet);
        personalizedDietButton = findViewById(R.id.personalized_diet);

        // Handle General Diet Plan button click
        generalDietButton.setOnClickListener(v -> {
            Intent intent = new Intent(DietOptionsActivity.this, DietActivity.class);
            startActivity(intent);
        });


        personalizedDietButton.setOnClickListener(v -> {

            Intent intent = new Intent(DietOptionsActivity.this, DietFormActivity.class);
            startActivity(intent);
        });


        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    startActivity(new Intent(this, UltrasoundActivity.class));
                    return true;
                case R.id.nav_scan:
                    startActivity(new Intent(this, UploadScanActivity.class));
                    return true;
                case R.id.nav_diet:
                    startActivity(new Intent(this, DietOptionsActivity.class));
                    return true;
                default:
                    return false;
            }
        });
    }
}
