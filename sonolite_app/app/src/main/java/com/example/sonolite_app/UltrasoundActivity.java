package com.example.sonolite_app;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Locale;

public class UltrasoundActivity extends AppCompatActivity {

    private Button uploadScanButton,uploadVideoButton, liveScanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultrasound);

        uploadScanButton = findViewById(R.id.uploadScanButton);
        liveScanButton = findViewById(R.id.liveScanButton);
        uploadVideoButton = findViewById(R.id.uploadVideoButton);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        uploadScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UltrasoundActivity.this, UploadScanActivity.class);
                startActivity(intent);
            }
        });

        uploadVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UltrasoundActivity.this, UploadVideoActivity.class);
                startActivity(intent);
            }
        });

        liveScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_scan:
                        startActivity(new Intent(UltrasoundActivity.this, MainActivity.class));
                        return true;
                    case R.id.nav_home:
                        return true; // Already in the home activity
                    case R.id.nav_diet:
                        startActivity(new Intent(UltrasoundActivity.this, DietOptionsActivity.class));
                        return true;
                }
                return false;
            }
        });

    }

    private void loadLanguage() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String languageCode = prefs.getString("Selected_Lang", "en"); // Default to English
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}