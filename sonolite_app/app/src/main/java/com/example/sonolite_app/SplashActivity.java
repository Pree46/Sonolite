package com.example.sonolite_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LanguageSelectionActivity.class);
            startActivity(intent);
            finish();
        }, 2000);
    }
}
