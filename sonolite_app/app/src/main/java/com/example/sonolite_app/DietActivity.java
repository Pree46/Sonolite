package com.example.sonolite_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class DietActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        setupHoverEffect(R.id.btnTrimester1, R.id.tvTrimester1, 1);
        setupHoverEffect(R.id.btnTrimester2, R.id.tvTrimester2, 2);
        setupHoverEffect(R.id.btnTrimester3, R.id.tvTrimester3, 3);
        setupHoverEffect(R.id.btnPostDelivery, R.id.tvPostDelivery, 4);
    }

    private void setupHoverEffect(int imageViewId, int textViewId, int trimester) {
        ImageView imageView = findViewById(imageViewId);
        TextView textView = findViewById(textViewId);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // Show text when pressed
                        textView.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_UP: // Hide text and navigate when released
                        textView.setVisibility(View.GONE);
                        openTrimesterActivity(trimester);
                        break;
                    case MotionEvent.ACTION_CANCEL: // Hide text if touch is canceled
                        textView.setVisibility(View.GONE);
                        break;
                }
                return true;
            }
        });
    }

    private void openTrimesterActivity(int trimester) {
        Intent intent = new Intent(DietActivity.this, TrimesterActivity.class);
        intent.putExtra("TRIMESTER", trimester);
        startActivity(intent);
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
