package com.example.sonolite_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class DietActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        ImageView btnTrimester1 = findViewById(R.id.btnTrimester1);
        ImageView btnTrimester2 = findViewById(R.id.btnTrimester2);
        ImageView btnTrimester3 = findViewById(R.id.btnTrimester3);
        ImageView btnPostDelivery = findViewById(R.id.btnPostDelivery);

        btnTrimester1.setOnClickListener(v -> startActivity(new Intent(DietActivity.this, Trimester1Activity.class)));
        btnTrimester2.setOnClickListener(v -> startActivity(new Intent(DietActivity.this, Trimester2Activity.class)));
        btnTrimester3.setOnClickListener(v -> startActivity(new Intent(DietActivity.this, Trimester3Activity.class)));
        btnPostDelivery.setOnClickListener(v -> startActivity(new Intent(DietActivity.this, PostDeliveryActivity.class)));
    }
}
