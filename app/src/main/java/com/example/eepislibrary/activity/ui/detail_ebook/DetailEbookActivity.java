package com.example.eepislibrary.activity.ui.detail_ebook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.eepislibrary.R;
import com.example.eepislibrary.api.ApiClient;
import com.example.eepislibrary.api.ApiInterface;
import com.example.eepislibrary.utils.CustomLoading;
import com.example.eepislibrary.utils.InvalidToken;
import com.example.eepislibrary.utils.Session;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEbookActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private Session session;
    private String id_ebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ebook);

        setTitle(getString(R.string.detail_ebook));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        session = new Session(getApplicationContext());

        id_ebook = Objects.requireNonNull(getIntent().getExtras()).getString("id_ebook");

        progressDialog = CustomLoading.getInstance(this);
        progressDialog.show();

        callDetailEbook();
    }

    private void callDetailEbook() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = api.getDetailEbook(session.getIdUser(), session.getToken(), id_ebook);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> res) {
                if (res.isSuccessful() && res.body() != null) {
                    String jsonResponse = res.body();
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);

                        String status = jsonObject.getString("status");
                        if(status.equals("success")){
                            try {
                                populateDetailEbook(jsonObject.getJSONObject("data"));
                            }
                            catch (JSONException e){
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            progressDialog.dismiss();
                            if(!jsonObject.getString("reason").equals("Invalid token")){
                                Snackbar.make(findViewById(R.id.view_detail_ebook), jsonObject.getString("reason"), Snackbar.LENGTH_LONG)
                                        .show();
                            }
                            else{
                                InvalidToken.backToLogin(getApplicationContext(), new DetailEbookActivity());
                            }
                        }

                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), R.string.system_error, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void populateDetailEbook(JSONObject jsonObject) throws JSONException{
        AppCompatImageView cover_ebook = findViewById(R.id.detail_ebook_cover_ebook);
        Picasso.get().load(jsonObject.getString("gambar")).into(cover_ebook);

        AppCompatTextView judul_ebook = findViewById(R.id.detail_ebook_judul_ebook);
        judul_ebook.setText(jsonObject.getString("judul"));

        AppCompatTextView sinopsis = findViewById(R.id.detail_ebook_sinopsis);
        sinopsis.setText(jsonObject.getString("sinopsis"));

        AppCompatTextView tahun = findViewById(R.id.detail_ebook_tahun);
        tahun.setText(jsonObject.getString("tahun"));

        AppCompatTextView pengarang = findViewById(R.id.detail_ebook_pengarang);
        pengarang.setText(jsonObject.getString("pengarang"));

        Button baca_ebook = findViewById(R.id.detail_ebook_btn_baca_ebook);
        String ebook = jsonObject.getString("ebook");
        baca_ebook.setOnClickListener(v -> {
            Intent chooser = Intent.createChooser(
                    new Intent(Intent.ACTION_VIEW)
                            .setDataAndType(Uri.parse(ebook), "application/pdf"),
                    getString(R.string.choose_app)
            );
            chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // optional
            startActivity(chooser);
        });

        progressDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}