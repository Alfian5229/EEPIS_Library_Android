package com.example.eepislibrary.activity.ui.detail_buku;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

public class DetailBukuActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private Session session;
    private String id_buku;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_buku);

        setTitle(getString(R.string.detail_buku));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        session = new Session(getApplicationContext());
        view = findViewById(R.id.view_detail_buku);

        id_buku = Objects.requireNonNull(getIntent().getExtras()).getString("id_buku");

        progressDialog = CustomLoading.getInstance(this);
        progressDialog.show();

        Button button = findViewById(R.id.detail_buku_btn_pesan_buku);
        button.setOnClickListener(v -> callApiPesanBuku());

        callDetailBuku();
    }

    private void callDetailBuku() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = api.getDetailBuku(session.getIdUser(), session.getToken(), id_buku);
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
                                populateDetailBuku(jsonObject.getJSONObject("data"));
                            }
                            catch (JSONException e){
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            progressDialog.dismiss();
                            if(!jsonObject.getString("reason").equals("Invalid token")){
                                Snackbar.make(findViewById(R.id.view_detail_buku), jsonObject.getString("reason"), Snackbar.LENGTH_LONG)
                                        .show();
                            }
                            else{
                                InvalidToken.backToLogin(getApplicationContext(), new DetailBukuActivity());
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

    private void populateDetailBuku(JSONObject jsonObject) throws JSONException{
        AppCompatImageView cover_buku = findViewById(R.id.detail_buku_cover_buku);
        Picasso.get().load(jsonObject.getString("gambar")).into(cover_buku);

        AppCompatTextView judul_buku = findViewById(R.id.detail_buku_judul_buku);
        judul_buku.setText(jsonObject.getString("judul"));

        AppCompatTextView sinopsis = findViewById(R.id.detail_buku_sinopsis);
        sinopsis.setText(jsonObject.getString("sinopsis"));

        AppCompatTextView tahun = findViewById(R.id.detail_buku_tahun);
        tahun.setText(jsonObject.getString("tahun"));

        AppCompatTextView pengarang = findViewById(R.id.detail_buku_pengarang);
        pengarang.setText(jsonObject.getString("pengarang"));

        progressDialog.dismiss();
    }

    private void callApiPesanBuku() {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = api.postPesan(session.getIdUser(), session.getToken(), id_buku);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> res) {
                if (res.isSuccessful() && res.body() != null) {
                    String jsonResponse = res.body();
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        Snackbar.make(view, jsonObject.getString("reason"), Snackbar.LENGTH_LONG)
                                .show();

                    } catch (JSONException e) {
                        Snackbar.make(view, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG)
                                .show();
                    }
                }
                else{
                    Snackbar.make(view, R.string.system_error, Snackbar.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Snackbar.make(view, Objects.requireNonNull(t.getMessage()), Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}