package com.example.sonolite_app;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Trimester1Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimester1);
    }

    public void openDetails(View view) {
        int id = view.getId();
        String title = "";
        String details = "";

        switch (id) {
            case R.id.vegetables_card:
                title = "Vegetables";
                details = "🥬 Spinach - High in folic acid\n🥦 Broccoli - Rich in iron and fiber";
                break;
            case R.id.fruits_card:
                title = "Fruits";
                details = "🍌 Banana - High in potassium\n🍊 Oranges - Vitamin C boosts immunity";
                break;
            case R.id.proteins_card:
                title = "Proteins";
                details = "🥚 Eggs - High in choline\n🐔 Chicken - Lean protein for growth";
                break;
        }

        showBottomSheet(title, details);
    }

    private void showBottomSheet(String title, String details) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);

        TextView titleView = view.findViewById(R.id.bottom_sheet_title);
        TextView detailsView = view.findViewById(R.id.bottom_sheet_details);

        titleView.setText(title);
        detailsView.setText(details);

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }
}
