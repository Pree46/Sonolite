package com.example.sonolite_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class LanguageSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        loadLanguage();

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

        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("Selected_Lang", languageCode);
        editor.apply();

        applyLanguage(languageCode);

        Intent intent = new Intent(LanguageSelectionActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void loadLanguage() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String languageCode = prefs.getString("Selected_Lang", "en"); // Default to English
        applyLanguage(languageCode);
    }

    private void applyLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}
