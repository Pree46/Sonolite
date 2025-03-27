package com.example.sonolite_app;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class Trimester1Activity extends AppCompatActivity {

    private LinearLayout vegetablesList, fruitsList, proteinsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimester1);

        // Initializing the views
        vegetablesList = findViewById(R.id.vegetables_list);
        fruitsList = findViewById(R.id.fruits_list);
        proteinsList = findViewById(R.id.proteins_list);
    }

    // Toggle methods (these should be outside onCreate)
    public void toggleVegetables(View view) {
        if (vegetablesList.getVisibility() == View.GONE) {
            vegetablesList.setVisibility(View.VISIBLE);
        } else {
            vegetablesList.setVisibility(View.GONE);
        }
    }

    public void toggleFruits(View view) {
        if (fruitsList.getVisibility() == View.GONE) {
            fruitsList.setVisibility(View.VISIBLE);
        } else {
            fruitsList.setVisibility(View.GONE);
        }
    }

    public void toggleProteins(View view) {
        if (proteinsList.getVisibility() == View.GONE) {
            proteinsList.setVisibility(View.VISIBLE);
        } else {
            proteinsList.setVisibility(View.GONE);
        }
    }
}
