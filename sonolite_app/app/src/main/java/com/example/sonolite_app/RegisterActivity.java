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

public class RegisterActivity extends AppCompatActivity {

    EditText username, aadhaar, password, confirmPassword;
    Button registerButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        username = findViewById(R.id.username);
        aadhaar = findViewById(R.id.aadhaar);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String adhaar = aadhaar.getText().toString().trim();

                if (adhaar.isEmpty()) {
                    aadhaar.setError("Aadhaar number is required");
                    return;
                }
                if (!adhaar.matches("\\d{12}")) {
                    aadhaar.setError("Enter a valid 12-digit Aadhaar number");
                    return;
                }

                String pass = password.getText().toString();
                String confirmPass = confirmPassword.getText().toString();

                if (user.isEmpty() || adhaar.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (!pass.equals(confirmPass)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    boolean registered = db.registerUser(user, adhaar, pass);
                    if (registered) {
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
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