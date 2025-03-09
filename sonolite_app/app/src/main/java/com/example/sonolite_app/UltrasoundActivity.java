package com.example.sonolite_app;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class UltrasoundActivity extends AppCompatActivity {

    private Button uploadScanButton, liveScanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultrasound);

        uploadScanButton = findViewById(R.id.uploadScanButton);
        liveScanButton = findViewById(R.id.liveScanButton);

        uploadScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UltrasoundActivity.this, UploadScanActivity.class);
                startActivity(intent);
            }
        });

        liveScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement live scan functionality
            }
        });
    }
}
