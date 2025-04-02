package com.example.sonolite_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LanguageSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        Button btnEnglish = findViewById(R.id.btnEnglish);
        Button btnHindi = findViewById(R.id.btnHindi);
        Button btnTamil = findViewById(R.id.btnTamil);

        btnEnglish.setOnClickListener(v -> setLanguage("en"));
        btnHindi.setOnClickListener(v -> setLanguage("hi"));
        btnTamil.setOnClickListener(v -> setLanguage("ta"));
    }

    private void setLanguage(String languageCode) {
        // Save selected language in SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("Selected_Lang", languageCode);
        editor.apply();  // Save changes

        // Set app language immediately
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Redirect to MainActivity
        Intent intent = new Intent(LanguageSelectionActivity.this, MainActivity.class);
        startActivity(intent);
        finish();  // Finish LanguageSelectionActivity so it doesn't come back
    }
}
