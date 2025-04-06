package com.example.sonolite_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    EditText aadhaarEditText, passwordEditText;
    Button loginButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        aadhaarEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_btn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aadhaar = aadhaarEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (aadhaar.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    boolean validUser = db.checkUser(aadhaar, password);
                    if (validUser) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, UltrasoundActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Aadhaar or Password", Toast.LENGTH_SHORT).show();
                    }
                }
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
