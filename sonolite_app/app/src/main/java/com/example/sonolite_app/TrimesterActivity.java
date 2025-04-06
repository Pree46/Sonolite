package com.example.sonolite_app;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Locale;

public class TrimesterActivity extends AppCompatActivity {

    private int trimester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimester1);


        trimester = getIntent().getIntExtra("TRIMESTER", 1);

    }


    public void openDetails(View view) {
        int id = view.getId();
        String title = "";
        int imageResId = 0;
        String[][] content = new String[][]{};
        TextView headingTextView = findViewById(R.id.trimester_heading);

        switch (trimester) {
            case 1:
                content = getTrimester1Data(id);
                headingTextView.setText(getString(R.string.trimester_1));
                break;
            case 2:
                content = getTrimester2Data(id);
                headingTextView.setText(getString(R.string.trimester_2));
                break;
            case 3:
                content = getTrimester3Data(id);
                headingTextView.setText(getString(R.string.trimester_3));
                break;
            case 4:
                content = getPostDeliveryData(id);
                headingTextView.setText(getString(R.string.post_delivery));
                break;
        }



        if (id == R.id.fruits_card) {
            title = getString(R.string.fruits);
            imageResId = R.drawable.fruits;
        } else if (id == R.id.vegetables_card) {
            title = getString(R.string.vegetables);
            imageResId = R.drawable.veggie_bsl;
        } else if (id == R.id.proteins_card) {
            title = getString(R.string.proteins);
            imageResId = R.drawable.plant_protein;
        } else if (id == R.id.animal_proteins_card) {
            title = getString(R.string.animal_proteins);
            imageResId = R.drawable.animal_protein_bsl;
        } else if (id == R.id.beverage_card) {
            title = getString(R.string.beverages);
            imageResId = R.drawable.beverage_bsl;
        } else if (id == R.id.grains_and_whole_foods) {
            title = getString(R.string.grains);
            imageResId = R.drawable.whole_food_bsl;
        } else if (id == R.id.nuts_and_seeds) {
            title = getString(R.string.nuts);
            imageResId = R.drawable.nuts_bsl;
        } else if (id == R.id.healthy_fats_and_oils) {
            title = getString(R.string.healthy_fats);
            imageResId = R.drawable.fats_bsl;
        }


        if (content.length > 0) {
            showBottomSheet(title, imageResId, content);
        }
    }


    private String[][] getTrimester1Data(int id) {
        return new String[][]{
                {getString(R.string.best_choices), getString(R.string.trim1_fruits_best)},
                {getString(R.string.limit_avoid), getString(R.string.trim1_fruits_avoid)}
        };
    }

    private String[][] getTrimester2Data(int id) {
        return new String[][]{
                {getString(R.string.best_choices), getString(R.string.trim2_fruits_best)},
                {getString(R.string.limit_avoid), getString(R.string.trim2_fruits_avoid)}
        };
    }

    private String[][] getTrimester3Data(int id) {
        return new String[][]{
                {getString(R.string.best_choices), getString(R.string.trim3_fruits_best)},
                {getString(R.string.limit_avoid), getString(R.string.trim3_fruits_avoid)}
        };
    }

    private String[][] getPostDeliveryData(int id) {
        return new String[][]{
                {getString(R.string.best_choices), getString(R.string.post_fruits_best)},
                {getString(R.string.limit_avoid), getString(R.string.post_fruits_avoid)}
        };
    }



    private void showBottomSheet(String title, int imageResId, String[][] content) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);

        TextView titleView = view.findViewById(R.id.bottom_sheet_title);
        ImageView imageView = view.findViewById(R.id.bottom_sheet_image);
        LinearLayout contentContainer = view.findViewById(R.id.content_container);
        Button closeButton = view.findViewById(R.id.close_button);


        titleView.setText(title);
        imageView.setImageResource(imageResId);

        contentContainer.removeAllViews();

        for (String[] section : content) {
            TextView sectionTitle = new TextView(this);
            sectionTitle.setText(section[0]);
            sectionTitle.setTextSize(18);
            sectionTitle.setTextColor(getResources().getColor(android.R.color.white));
            sectionTitle.setTypeface(null, Typeface.BOLD);
            sectionTitle.setPadding(0, 10, 0, 5);
            contentContainer.addView(sectionTitle);

            TextView sectionContent = new TextView(this);
            sectionContent.setText(section[1]);
            sectionContent.setTextSize(16);
            sectionContent.setTextColor(getResources().getColor(android.R.color.darker_gray));
            sectionContent.setPadding(0, 0, 0, 12);
            contentContainer.addView(sectionContent);
        }

        closeButton.setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
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
