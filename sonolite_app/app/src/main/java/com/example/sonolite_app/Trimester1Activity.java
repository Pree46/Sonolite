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

public class Trimester1Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimester1);
    }

    public void openDetails(View view) {
        int id = view.getId();
        String title = "";
        int imageResId = R.drawable.fruits;
        String[][] content = new String[][]{};

        switch (id) {
            case R.id.fruits_card:
                title = "🥭 Fruits";
                imageResId = R.drawable.fruits;
                content = new String[][]{
                        {"✅ Eat:", "🍊 Citrus fruits – Boosts Immunity\n🍌 Bananas – Supports Digestion\n🍎 Apples – Fiber-Rich\n🥑 Avocados – Healthy Fats\n🍓 Berries – Antioxidants"},
                        {"❌ Avoid:", "Unwashed fruits, excess pineapple & papaya, canned fruits with sugar"}
                };
                break;

            case R.id.vegetables_card:
                title = "🥦 Vegetables";
                imageResId = R.drawable.veggie_bsl;
                content = new String[][]{
                        {"✅ Eat:", "🥬 Leafy Greens – Iron & Folic Acid\n🥕 Carrots – Eye Development\n🌶️ Bell Peppers – Vitamin C"},
                        {"❌ Avoid:", "Raw sprouts, unwashed vegetables"}
                };
                break;

            case R.id.animal_proteins_card:
                title = "🍗 Animal-Based Proteins";
                imageResId = R.drawable.animal_protein_bsl;
                content = new String[][]{
                        {"✅ Eat:", "🐔 Chicken – High Protein\n🐟 Salmon – Omega-3\n🥚 Eggs – Choline for Brain"},
                        {"❌ Avoid:", "Raw meats, processed meats, high-mercury fish"}
                };
                break;

            case R.id.proteins_card:
                title = "🥑 Plant-Based Proteins";
                imageResId = R.drawable.plant_protein;
                content = new String[][]{
                        {"✅ Eat:", "🌱 Legumes – Fiber & Folate\n🍚 Quinoa – Complete Protein\n🍛 Tofu – Good Protein Source"},
                        {"❌ Avoid:", "Uncooked legumes & soy"}
                };
                break;

            case R.id.nuts_and_seeds:
                title = "🥜 Nuts & Seeds";
                imageResId = R.drawable.nuts_bsl;
                content = new String[][]{
                        {"✅ Eat:", "🌰 Almonds – Vitamin E\n🥜 Walnuts – Omega-3\n🌿 Chia Seeds – Healthy Fats"},
                        {"❌ Avoid:", "Excess nuts, bitter almonds"}
                };
                break;

            case R.id.grains_and_whole_foods:
                title = "🍞 Grains & Whole Foods";
                imageResId = R.drawable.whole_food_bsl;
                content = new String[][]{
                        {"✅ Eat:", "🍚 Brown Rice – High Fiber\n🥣 Oats – Good Digestion\n🥖 Whole Wheat Bread – Energy"},
                        {"❌ Avoid:", "White bread, refined grains"}
                };
                break;

            case R.id.healthy_fats_and_oils:
                title = "🧈 Healthy Fats & Oils";
                imageResId = R.drawable.fats_bsl;
                content = new String[][]{
                        {"✅ Eat:", "🫒 Olive Oil – Heart-Healthy\n🥑 Avocados – Monounsaturated Fats\n🥥 Coconut Oil – Metabolism Boost"},
                        {"❌ Avoid:", "Trans fats, hydrogenated oils"}
                };
                break;

            case R.id.beverage_card:
                title = "🚰 Beverages";
                imageResId = R.drawable.beverage_bsl;
                content = new String[][]{
                        {"✅ Drink:", "💧 Water – Hydration\n🥥 Coconut Water – Electrolytes\n🍵 Herbal Teas – Relaxation"},
                        {"❌ Avoid:", "Alcohol, excess caffeine, sugary sodas"}
                };
                break;
        }

        showBottomSheet(title, imageResId, content);
    }


    private void showBottomSheet(String title, int imageResId, String[][] content) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);

        TextView titleView = view.findViewById(R.id.bottom_sheet_title);
        ImageView imageView = view.findViewById(R.id.bottom_sheet_image);
        LinearLayout contentContainer = view.findViewById(R.id.content_container);
        Button moreInfoButton = view.findViewById(R.id.more_info_button);

        titleView.setText(title);
        imageView.setImageResource(imageResId);

        // Clear old views if any
        contentContainer.removeAllViews();

        // Dynamically add content
        for (String[] section : content) {
            // Section Title
            TextView sectionTitle = new TextView(this);
            sectionTitle.setText(section[0]);
            sectionTitle.setTextSize(18);
            sectionTitle.setTextColor(getResources().getColor(android.R.color.white));
            sectionTitle.setTypeface(null, Typeface.BOLD);
            sectionTitle.setPadding(0, 10, 0, 5);
            contentContainer.addView(sectionTitle);

            // Section Content
            TextView sectionContent = new TextView(this);
            sectionContent.setText(section[1]);
            sectionContent.setTextSize(16);
            sectionContent.setTextColor(getResources().getColor(android.R.color.white));
            contentContainer.addView(sectionContent);
        }

        // Button Click Event (Optional)
        moreInfoButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.example.com"));
            startActivity(intent);
        });

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }

}
