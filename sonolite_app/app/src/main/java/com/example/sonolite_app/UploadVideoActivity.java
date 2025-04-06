package com.example.sonolite_app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.VideoView;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

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

public class UploadVideoActivity extends AppCompatActivity {

    private static final int PICK_VIDEO_REQUEST = 1;
    private static final String API_URL = "http://10.0.2.2:8000/";

    private VideoView videoView;
    private Uri videoUri;
    private Button uploadButton, downloadButton;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadvideo);

        videoView = findViewById(R.id.videoView);
        uploadButton = findViewById(R.id.uploadButton);
        downloadButton = findViewById(R.id.downloadVideoButton);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        apiService = retrofit.create(ApiService.class);

        uploadButton.setOnClickListener(v -> pickVideo());
        downloadButton.setOnClickListener(v -> downloadVideo());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(UploadVideoActivity.this, UltrasoundActivity.class));
                        return true;
                    case R.id.nav_scan:
                        return true; // Already in the scan activity
                    case R.id.nav_diet:
                        startActivity(new Intent(UploadVideoActivity.this, DietOptionsActivity.class));
                }
                return false;
            }
        });
    }

    private void pickVideo() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");
        startActivityForResult(intent, PICK_VIDEO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_VIDEO_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            videoUri = data.getData();
            videoView.setVideoURI(videoUri);
            videoView.setMediaController(new MediaController(this));
            videoView.start();
            uploadVideo(videoUri);
        }
    }

    private void uploadVideo(Uri videoUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(videoUri);
            File file = new File(getCacheDir(), "upload_video.mp4");

            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            fos.close();

            RequestBody requestBody = RequestBody.create(file, MediaType.parse("video/mp4"));
            MultipartBody.Part videoPart = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

            Call<ResponseBody> call = apiService.uploadVideo(videoPart);
            call.enqueue(new retrofit2.Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(UploadVideoActivity.this, "Video uploaded successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UploadVideoActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(UploadVideoActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to read video", Toast.LENGTH_SHORT).show();
        }
    }

    private void downloadVideo() {
        String processedVideoUrl = API_URL + "outputs/processed_upload_video.mp4";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(processedVideoUrl).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e("Download Error", "Server error: " + response.code());
                    runOnUiThread(() -> Toast.makeText(UploadVideoActivity.this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show());
                    return;
                }

                File videoFile = new File(getExternalFilesDir(Environment.DIRECTORY_MOVIES), "processed_video.mp4");
                FileOutputStream fos = new FileOutputStream(videoFile);
                fos.write(response.body().bytes());
                fos.close();

                Log.d("Download", "Video saved at: " + videoFile.getAbsolutePath());
                Log.d("Download", "File exists: " + videoFile.exists());
                Log.d("Download", "File size: " + videoFile.length() + " bytes");

                runOnUiThread(() -> {
                    VideoView videoView = findViewById(R.id.videoView);
                    MediaController mediaController = new MediaController(UploadVideoActivity.this);
                    mediaController.setAnchorView(videoView);

                    Uri videoUri = Uri.fromFile(videoFile);

                    Log.d("VideoDebug", "Playing video from: " + videoFile.getAbsolutePath());
                    Log.d("VideoDebug", "File exists: " + videoFile.exists());
                    Log.d("VideoDebug", "File size: " + videoFile.length() + " bytes");

                    videoView.stopPlayback();
                    videoView.setVideoURI(null);

                    videoView.setVideoURI(videoUri);
                    videoView.setMediaController(mediaController);
                    videoView.requestFocus();
                    videoView.start();
                });
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(UploadVideoActivity.this, "Download Failed!", Toast.LENGTH_SHORT).show());
            }
        });
    }



    interface ApiService {
        @Multipart
        @POST("/process_video/")
        Call<ResponseBody> uploadVideo(@Part MultipartBody.Part file);
    }
}
