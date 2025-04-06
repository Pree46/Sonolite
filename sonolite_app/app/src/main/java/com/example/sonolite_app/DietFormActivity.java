package com.example.sonolite_app;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.json.JSONException;
import java.io.UnsupportedEncodingException;
import android.util.Log;


public class DietFormActivity extends AppCompatActivity {

    Spinner spinnerTrimester;
    CheckBox milk, yogurt, nuts, gluten;
    RadioGroup radioBP, radioSugar, radioDiet;
    EditText editUserName, editDoctorName;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_form);

        spinnerTrimester = findViewById(R.id.spinner_trimester);
        milk = findViewById(R.id.checkbox_milk);
        yogurt = findViewById(R.id.checkbox_yogurt);
        nuts = findViewById(R.id.checkbox_nuts);
        gluten = findViewById(R.id.checkbox_gluten);
        radioBP = findViewById(R.id.radio_bp);
        radioSugar = findViewById(R.id.radio_sugar);
        radioDiet = findViewById(R.id.radio_diet);
        editUserName = findViewById(R.id.edit_user_name);
        editDoctorName = findViewById(R.id.edit_doctor_name);
        buttonSubmit = findViewById(R.id.button_submit);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.trimester_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTrimester.setAdapter(adapter);

        buttonSubmit.setOnClickListener(view -> submitForm());
    }

    private void submitForm() {
        String trimester = spinnerTrimester.getSelectedItem().toString();
        ArrayList<String> allergies = new ArrayList<>();
        if (milk.isChecked()) allergies.add("Milk");
        if (yogurt.isChecked()) allergies.add("Yogurt");
        if (nuts.isChecked()) allergies.add("Nuts");
        if (gluten.isChecked()) allergies.add("Gluten");

        String bp = ((RadioButton) findViewById(radioBP.getCheckedRadioButtonId())).getText().toString();
        String sugar = ((RadioButton) findViewById(radioSugar.getCheckedRadioButtonId())).getText().toString();
        String diet = ((RadioButton) findViewById(radioDiet.getCheckedRadioButtonId())).getText().toString().toLowerCase();
        String user = editUserName.getText().toString();
        String doctor = editDoctorName.getText().toString();

        try {
            JSONObject data = new JSONObject();
            data.put("trimester", trimester);
            data.put("allergies", new JSONArray(allergies));
            data.put("blood_pressure", bp);
            data.put("blood_sugar", sugar);
            data.put("diet_type", diet);
            data.put("user_name", user);
            data.put("doctor_name", doctor);
            data.put("logo_path", "");

            String url = "http://10.0.2.2:5000/generate_diet_pdf";

            Request<byte[]> pdfRequest = new Request<byte[]>(Request.Method.POST, url,
                    error -> {
                        Log.e("PDF_ERROR", "Volley Error", error);
                        Toast.makeText(this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }) {

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return data.toString().getBytes("utf-8");
                    } catch (UnsupportedEncodingException e) {
                        throw new AuthFailureError("Encoding error", e);
                    }
                }

                @Override
                protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
                    return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
                }

                @Override
                protected void deliverResponse(byte[] response) {
                    try {
                        String filename = user.replace(" ", "_") + "_DietPlan.pdf";
                        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                        if (!path.exists()) path.mkdirs();
                        File file = new File(path, filename);
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(response);
                        fos.close();

                        new AlertDialog.Builder(DietFormActivity.this)
                                .setTitle("Success")
                                .setMessage("Diet Plan saved to Downloads. Open now?")
                                .setPositiveButton("Open", (dialog, which) -> openPdf(filename))
                                .setNegativeButton("Cancel", null)
                                .show();

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(DietFormActivity.this, "Failed to save PDF", Toast.LENGTH_LONG).show();
                    }
                }
            };

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(pdfRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private void downloadPdfFile(String filename) {
        String url = "http://10.0.2.2:5000/" + filename;

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("Downloading Diet Plan");
        request.setDescription("Downloading PDF...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);

        // Show success dialog
        new AlertDialog.Builder(this)
                .setTitle("Success!")
                .setMessage("Diet Plan downloaded. Do you want to open it now?")
                .setPositiveButton("Open", (dialog, which) -> openPdf(filename))
                .setNegativeButton("Later", null)
                .show();
    }

    private void openPdf(String filename) {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path, filename);
        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

}
