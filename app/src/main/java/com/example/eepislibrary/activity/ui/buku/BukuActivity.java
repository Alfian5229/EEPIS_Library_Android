package com.example.eepislibrary.activity.ui.buku;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.eepislibrary.R;
import com.example.eepislibrary.adapter.list_buku.ListBukuAdapter;
import com.example.eepislibrary.adapter.list_buku.ListBukuAdapterItems;
import com.example.eepislibrary.api.ApiClient;
import com.example.eepislibrary.api.ApiInterface;
import com.example.eepislibrary.utils.CustomLoading;
import com.example.eepislibrary.utils.InvalidToken;
import com.example.eepislibrary.utils.Session;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BukuActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ArrayList<ListBukuAdapterItems> listBukuData = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private GridView gridView;
    private Session session;
    private ProgressDialog progressDialog;
    private TextView tv_list_buku_empty;
    private String newText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_buku);

        setTitle(getString(R.string.pesan_buku));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        gridView = findViewById(R.id.gv_list_buku);
        tv_list_buku_empty = findViewById(R.id.tv_list_buku_empty);
        session = new Session(getApplicationContext());

        swipeRefreshLayout = findViewById(R.id.swipe_view_list_buku);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            populateListBuku(newText);
            swipeRefreshLayout.setRefreshing(false);
        });

        progressDialog = CustomLoading.getInstance(this);
        progressDialog.show();

        populateListBuku(null);
    }

    private void populateListBuku(String newText) {
        listBukuData.clear();
        callApiListBuku(newText);
    }

    private void callApiListBuku(String newText) {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = api.getListBuku(session.getIdUser(), session.getToken(), newText);
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
                                initListBuku(jsonObject);
                            }
                            catch (JSONException e){
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            progressDialog.dismiss();
                            if(!jsonObject.getString("reason").equals("Invalid token")){
                                Snackbar.make(findViewById(R.id.view_list_buku), jsonObject.getString("reason"), Snackbar.LENGTH_LONG)
                                        .show();
                            }
                            else{
                                InvalidToken.backToLogin(getApplicationContext(), new BukuActivity());
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

    private void initListBuku(JSONObject jsonObject) throws JSONException {
        JSONArray data = jsonObject.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);

            listBukuData.add(new ListBukuAdapterItems(
                    object.getString("id"),
                    object.getString("gambar"),
                    object.getString("judul")
            ));
        }

        ListBukuAdapter listBukuAdapter = new ListBukuAdapter(getApplicationContext(), listBukuData);
        gridView.setAdapter(listBukuAdapter);

        if (listBukuAdapter.isEmpty()) {
            swipeRefreshLayout.setVisibility(View.GONE);
            gridView.setVisibility(View.GONE);
            tv_list_buku_empty.setVisibility(View.VISIBLE);
        } else {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.VISIBLE);
            tv_list_buku_empty.setVisibility(View.GONE);
        }

        progressDialog.dismiss();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        this.newText = newText;
        progressDialog.show();
        populateListBuku(newText);

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_buku, menu);
        MenuItem menuItem = menu.findItem(R.id.search_list_buku);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Cari...");
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}