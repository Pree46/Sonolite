package com.example.sonolite_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    private Button btnGetStarted;
    private ViewPager viewPager;
    private PregnancyFactsAdapter factsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnGetStarted = findViewById(R.id.btnGetStarted);
        viewPager = findViewById(R.id.viewPager);

        // Get Started Button Click
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, UltrasoundActivity.class);
                startActivity(intent);
            }
        });

        // Pregnancy Facts Data
        List<String> facts = new ArrayList<>();
        facts.add("🍼 Babies can hear sounds from inside the womb!");
        facts.add("🥑 Eating healthy fats helps baby’s brain development.");
        facts.add("🤰 Walking daily improves blood circulation during pregnancy.");

        factsAdapter = new PregnancyFactsAdapter(this, facts);
        viewPager.setAdapter(factsAdapter);

        viewPager.setPageMargin(40); // Adjust this as needed

    }
}
