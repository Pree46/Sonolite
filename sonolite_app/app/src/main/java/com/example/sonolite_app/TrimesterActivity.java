package com.example.sonolite_app;

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

public class TrimesterActivity extends AppCompatActivity {

    private int trimester; // Store the trimester number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimester1);

        // Get trimester from intent
        trimester = getIntent().getIntExtra("TRIMESTER", 1); // Default is 1st trimester

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
                headingTextView.setText("Trimester 1");
                break;
            case 2:
                content = getTrimester2Data(id);
                headingTextView.setText("Trimester 2");
                break;
            case 3:
                content = getTrimester3Data(id);
                headingTextView.setText("Trimester 3");
                break;
            case 4:
                content = getPostDeliveryData(id);
                headingTextView.setText("Post Delivery Diet");
                break;
        }

        // Dynamically set title & image based on selected category
        if (id == R.id.fruits_card) {
            title = "Fruits";
            imageResId = R.drawable.fruits;
        } else if (id == R.id.vegetables_card) {
            title = "Vegetables";
            imageResId = R.drawable.veggie_bsl;
        } else if (id == R.id.proteins_card) {
            title = "Plant Proteins";
            imageResId = R.drawable.plant_protein;
        } else if (id == R.id.animal_proteins_card) {
            title = "Animal Proteins & Dairy";
            imageResId = R.drawable.animal_protein_bsl;
        } else if (id == R.id.beverage_card) {
            title = "Beverages";
            imageResId = R.drawable.beverage_bsl;
        } else if (id == R.id.grains_and_whole_foods) {
            title = "Grains & Whole Foods";
            imageResId = R.drawable.whole_food_bsl;
        } else if (id == R.id.nuts_and_seeds) {
            title = "Nuts & Seeds";
            imageResId = R.drawable.nuts_bsl;
        } else if (id == R.id.healthy_fats_and_oils) {
            title = "Healthy Fats & Oils";
            imageResId = R.drawable.fats_bsl;
        }

        if (content.length > 0) {
            showBottomSheet(title, imageResId, content);
        }
    }


    private String[][] getTrimester1Data(int id) {
        switch (id) {
            case R.id.fruits_card:
                return new String[][]{
                        {"✅ Best Choices:", "🍊 Citrus fruits – Boosts immunity & absorbs iron\n🍌 Bananas – Reduces nausea\n🍎 Apples – Prevents constipation\n🍓 Berries – Brain development"},
                        {"❌ Limit / Avoid:", "⚠️ Unwashed fruits\n⚠️ Excess pineapple & papaya"}
                };
            case R.id.vegetables_card:
                return new String[][]{
                        {"✅ Best Choices:", "🥬 Leafy Greens – High in folic acid\n🥕 Carrots – Eye development\n🌶️ Bell Peppers – Immunity boost"},
                        {"❌ Limit / Avoid:", "⚠️ Raw sprouts\n⚠️ Unwashed veggies"}
                };
            case R.id.proteins_card:
                return new String[][]{
                        {"✅ Best Choices:", "🌱 Lentils – Rich in folic acid\n🍠 Sweet potatoes – Iron & fiber"},
                        {"❌ Limit / Avoid:", "⚠️ Overcooked beans (nutrient loss)"}
                };
            case R.id.animal_proteins_card:
                return new String[][]{
                        {"✅ Best Choices:", "🐟 Salmon – Omega-3s for baby’s brain\n🥚 Eggs – Choline for cognitive health"},
                        {"❌ Limit / Avoid:", "⚠️ Raw meat & seafood\n⚠️ Processed meats"}
                };
            case R.id.beverage_card:
                return new String[][]{
                        {"✅ Best Choices:", "💧 Water – Essential for amniotic fluid\n🥥 Coconut Water – Natural electrolytes\n🍵 Ginger Tea – Helps with nausea"},
                        {"❌ Limit / Avoid:", "⚠️ Alcohol & excess caffeine"}
                };
            case R.id.grains_and_whole_foods:
                return new String[][]{
                        {"✅ Best Choices:", "🍚 Brown rice – Provides steady energy\n🌾 Whole grains – Fiber & B vitamins"},
                        {"❌ Limit / Avoid:", "⚠️ Refined grains"}
                };
            case R.id.nuts_and_seeds:
                return new String[][]{
                        {"✅ Best Choices:", "🌰 Walnuts – Omega-3s\n🥜 Almonds – Healthy fats"},
                        {"❌ Limit / Avoid:", "⚠️ Salted nuts (high sodium)"}
                };
            case R.id.healthy_fats_and_oils:
                return new String[][]{
                        {"✅ Best Choices:", "🥑 Avocado – Prevents leg cramps\n🫒 Olive oil – Good cholesterol"},
                        {"❌ Limit / Avoid:", "⚠️ Excess fried food"}
                };
        }
        return new String[][]{};
    }

    private String[][] getTrimester2Data(int id) {
        switch (id) {
            case R.id.fruits_card:
                return new String[][]{
                        {"✅ Best Choices:", "🍍 Pineapple (Moderation) – Aids digestion\n🥑 Avocado – Healthy fats for brain\n🥬 Spinach – Iron for oxygen supply"},
                        {"❌ Limit / Avoid:", "⚠️ Unripe papaya\n⚠️ Excess grapes (high sugar)"}
                };
            case R.id.vegetables_card:
                return new String[][]{
                        {"✅ Best Choices:", "🥕 Carrots – Boosts eye health\n🥒 Cucumbers – Hydration & digestion\n🥬 Kale – Rich in calcium"},
                        {"❌ Limit / Avoid:", "⚠️ Excess raw sprouts\n⚠️ Undercooked mushrooms"}
                };
            case R.id.proteins_card:
                return new String[][]{
                        {"✅ Best Choices:", "🌱 Lentils – High in fiber & iron\n🍠 Sweet potatoes – Helps fetal growth"},
                        {"❌ Limit / Avoid:", "⚠️ Soy in excess (may disrupt hormones)"}
                };
            case R.id.animal_proteins_card:
                return new String[][]{
                        {"✅ Best Choices:", "🥚 Eggs – Rich in choline for baby’s brain\n🧀 Cheese – Calcium for bones"},
                        {"❌ Limit / Avoid:", "⚠️ Soft cheeses (risk of listeria)\n⚠️ Undercooked meat"}
                };
            case R.id.beverage_card:
                return new String[][]{
                        {"✅ Best Choices:", "🥥 Coconut water – Natural electrolytes\n🍵 Herbal teas (Ginger, Peppermint)"},
                        {"❌ Limit / Avoid:", "⚠️ Excess coffee & soft drinks"}
                };
            case R.id.grains_and_whole_foods:
                return new String[][]{
                        {"✅ Best Choices:", "🍚 Quinoa – Protein & fiber\n🍞 Whole wheat bread – B vitamins"},
                        {"❌ Limit / Avoid:", "⚠️ White bread & processed grains"}
                };
            case R.id.nuts_and_seeds:
                return new String[][]{
                        {"✅ Best Choices:", "🥜 Almonds – Healthy fats\n🌰 Walnuts – Brain health"},
                        {"❌ Limit / Avoid:", "⚠️ Salted & roasted nuts"}
                };
            case R.id.healthy_fats_and_oils:
                return new String[][]{
                        {"✅ Best Choices:", "🥑 Avocados – Omega-3 fatty acids\n🫒 Olive oil – Healthy for the heart"},
                        {"❌ Limit / Avoid:", "⚠️ Deep-fried foods"}
                };
        }
        return new String[][]{};
    }


    private String[][] getTrimester3Data(int id) {
        switch (id) {
            case R.id.fruits_card:
                return new String[][]{
                        {"✅ Best Choices:", "🥝 Kiwi – High vitamin C\n🍠 Sweet potatoes – Supports fetal growth"},
                        {"❌ Limit / Avoid:", "⚠️ Excess salty foods"}
                };
            case R.id.vegetables_card:
                return new String[][]{
                        {"✅ Best Choices:", "🥦 Broccoli – Calcium & fiber\n🫑 Bell peppers – Vitamin C"},
                        {"❌ Limit / Avoid:", "⚠️ Overly spicy foods"}
                };
            case R.id.proteins_card:
                return new String[][]{
                        {"✅ Best Choices:", "🌾 Legumes – Rich in protein\n🍠 Yams – Good for digestion"},
                        {"❌ Limit / Avoid:", "⚠️ Processed soy products"}
                };
            case R.id.animal_proteins_card:
                return new String[][]{
                        {"✅ Best Choices:", "🥩 Lean meats – Iron & protein\n🧀 Hard cheeses – Rich in calcium"},
                        {"❌ Limit / Avoid:", "⚠️ Raw seafood & sushi"}
                };
            case R.id.beverage_card:
                return new String[][]{
                        {"✅ Best Choices:", "🥤 Fresh fruit juices (no sugar)\n💧 Plenty of water"},
                        {"❌ Limit / Avoid:", "⚠️ Carbonated sodas"}
                };
            case R.id.grains_and_whole_foods:
                return new String[][]{
                        {"✅ Best Choices:", "🌾 Millet – High fiber\n🍞 Whole grain bread – Vitamin B"},
                        {"❌ Limit / Avoid:", "⚠️ White rice"}
                };
            case R.id.nuts_and_seeds:
                return new String[][]{
                        {"✅ Best Choices:", "🥜 Cashews – Good magnesium source\n🌰 Chia seeds – Omega-3s"},
                        {"❌ Limit / Avoid:", "⚠️ Peanut butter (high in added sugars)"}
                };
            case R.id.healthy_fats_and_oils:
                return new String[][]{
                        {"✅ Best Choices:", "🥑 Avocados – Prevents leg cramps\n🫒 Olive oil – Healthy for cooking"},
                        {"❌ Limit / Avoid:", "⚠️ Too much butter"}
                };
        }
        return new String[][]{};
    }


    private String[][] getPostDeliveryData(int id) {
        switch (id) {
            case R.id.fruits_card:
                return new String[][]{
                        {"✅ Best Choices:", "🥭 Papaya – Supports milk production\n🍌 Bananas – Boosts energy"},
                        {"❌ Limit / Avoid:", "⚠️ Citrus fruits in excess"}
                };
            case R.id.vegetables_card:
                return new String[][]{
                        {"✅ Best Choices:", "🥦 Broccoli – Supports healing\n🥕 Carrots – Boosts eyesight"},
                        {"❌ Limit / Avoid:", "⚠️ Gas-producing veggies (cabbage, onions)"}
                };
            case R.id.proteins_card:
                return new String[][]{
                        {"✅ Best Choices:", "🌾 Chickpeas – Boosts milk production\n🌰 Almonds – Protein & healthy fats"},
                        {"❌ Limit / Avoid:", "⚠️ Highly processed soy"}
                };
            case R.id.animal_proteins_card:
                return new String[][]{
                        {"✅ Best Choices:", "🥩 Lean meat – Speeds up recovery\n🥚 Eggs – Rich in protein"},
                        {"❌ Limit / Avoid:", "⚠️ Soft cheeses"}
                };
            case R.id.beverage_card:
                return new String[][]{
                        {"✅ Best Choices:", "🥛 Warm milk – Aids relaxation\n💧 Hydration – Key for recovery"},
                        {"❌ Limit / Avoid:", "⚠️ Alcohol & excess caffeine"}
                };
            case R.id.grains_and_whole_foods:
                return new String[][]{
                        {"✅ Best Choices:", "🍞 Whole wheat – Sustains energy\n🌾 Oats – Boosts breast milk production"},
                        {"❌ Limit / Avoid:", "⚠️ White bread"}
                };
            case R.id.nuts_and_seeds:
                return new String[][]{
                        {"✅ Best Choices:", "🥜 Almonds – Rich in vitamin E\n🌰 Walnuts – Brain booster"},
                        {"❌ Limit / Avoid:", "⚠️ High-sodium packaged nuts"}
                };
            case R.id.healthy_fats_and_oils:
                return new String[][]{
                        {"✅ Best Choices:", "🥑 Avocado – Supports postpartum health\n🫒 Olive oil – Anti-inflammatory"},
                        {"❌ Limit / Avoid:", "⚠️ Deep-fried snacks"}
                };
        }
        return new String[][]{};
    }


    private void showBottomSheet(String title, int imageResId, String[][] content) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);

        TextView titleView = view.findViewById(R.id.bottom_sheet_title);
        ImageView imageView = view.findViewById(R.id.bottom_sheet_image);
        LinearLayout contentContainer = view.findViewById(R.id.content_container);
        Button closeButton = view.findViewById(R.id.close_button);

        // Set the dynamic title and image
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

}
