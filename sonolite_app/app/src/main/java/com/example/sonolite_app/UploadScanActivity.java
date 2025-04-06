package com.example.sonolite_app;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class UploadScanActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;


    public static final String API_URL = "http://10.0.2.2:8000/";

    private ImageView imageView;
    private Button uploadButton, downloadPdfButton;
    private Bitmap annotatedImage;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadscan);

        imageView = findViewById(R.id.imageView);
        uploadButton = findViewById(R.id.uploadButton);
        downloadPdfButton = findViewById(R.id.downloadPdfButton);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);


        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        apiService = retrofit.create(ApiService.class);

        uploadButton.setOnClickListener(v -> pickImage());
        downloadPdfButton.setOnClickListener(v -> {
            if (annotatedImage != null) {
                saveImageAsPdf(annotatedImage);
            } else {
                Toast.makeText(UploadScanActivity.this, "No image to save!", Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(UploadScanActivity.this, UltrasoundActivity.class));
                        return true;
                    case R.id.nav_scan:
                        return true; // Already in the scan activity
                    case R.id.nav_diet:
                        startActivity(new Intent(UploadScanActivity.this, DietOptionsActivity.class));
                }
                return false;
            }
        });
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                uploadImage(imageUri);
            }
        }
    }

    private void uploadImage(Uri imageUri) {
        try {
            // ✅ FIX: Read image properly using ByteArrayOutputStream
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();

            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            RequestBody requestBody = RequestBody.create(imageBytes, MediaType.parse("image/jpeg"));
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("file", "image.jpg", requestBody);

            Call<ResponseBody> call = apiService.uploadImage(imagePart);
            call.enqueue(new retrofit2.Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        try {

                            byte[] imageBytes = response.body().bytes();
                            annotatedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                            if (annotatedImage != null) {
                                imageView.setImageBitmap(annotatedImage);
                            } else {
                                Toast.makeText(UploadScanActivity.this, "Failed to load image", Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(UploadScanActivity.this, "Error processing image", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UploadScanActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(UploadScanActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to read image", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImageAsPdf(Bitmap bitmap) {
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        page.getCanvas().drawBitmap(bitmap, 0, 0, null);
        pdfDocument.finishPage(page);

        File pdfFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "annotated_scan.pdf");
        try {
            FileOutputStream fos = new FileOutputStream(pdfFile);
            pdfDocument.writeTo(fos);
            fos.close();
            pdfDocument.close();
            Toast.makeText(this, "PDF saved successfully!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save PDF", Toast.LENGTH_SHORT).show();
        }
    }



    interface ApiService {
        @Multipart
        @POST("predict/")
        Call<ResponseBody> uploadImage(@Part MultipartBody.Part file);
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
