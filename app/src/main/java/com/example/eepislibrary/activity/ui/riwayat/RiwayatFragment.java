package com.example.eepislibrary.activity.ui.riwayat;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.eepislibrary.R;
import com.example.eepislibrary.adapter.riwayat.RiwayatAdapter;
import com.example.eepislibrary.adapter.riwayat.RiwayatAdapterItems;
import com.example.eepislibrary.api.ApiClient;
import com.example.eepislibrary.api.ApiInterface;
import com.example.eepislibrary.utils.Session;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatFragment extends Fragment {

    private ArrayList<RiwayatAdapterItems> listRiwayatData = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private TextView tvEmpty;
    private Session session;
    private View view;
    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RiwayatFactory riwayatFactory = new RiwayatFactory(requireActivity().getApplication());
        RiwayatViewModel riwayatViewModel = new ViewModelProvider(this, riwayatFactory)
                .get(RiwayatViewModel.class);
        View root = inflater.inflate(R.layout.fragment_riwayat, container, false);

        init(root);
        riwayatViewModel.getText().observe(getViewLifecycleOwner(), tvEmpty::setText);
        return root;
    }

    private void init(View view){
        listView = view.findViewById(R.id.lv_riwayat);
        tvEmpty = view.findViewById(R.id.tv_riwayat_empty);
        session = new Session(getContext());

        swipeRefreshLayout = view.findViewById(R.id.swipe_view_riwayat);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            populateRiwayat();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        progressDialog = new ProgressDialog(getContext(),
                R.style.LoginTheme_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
        populateRiwayat();
    }

    private void populateRiwayat() {
        listRiwayatData.clear();
        callApiRiwayat();
    }

    private void callApiRiwayat() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = api.postRiwayat(session.getIdUser(), session.getToken());
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
                                initListRiwayat(jsonObject);
                            }
                            catch (JSONException e){
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Snackbar.make(view, jsonObject.getString("reason"), Snackbar.LENGTH_LONG)
                                    .show();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getContext(), R.string.system_error, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initListRiwayat(JSONObject jsonObject) throws JSONException {
        JSONArray data = jsonObject.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);

            listRiwayatData.add(new RiwayatAdapterItems(
                    object.getString("gambar"),
                    object.getString("judul"),
                    object.getString("tgl_pinjam"),
                    object.getString("tgl_kembali")
            ));
        }

        RiwayatAdapter riwayatAdapter = new RiwayatAdapter(listRiwayatData, getContext());
        listView.setAdapter(riwayatAdapter);

        if(riwayatAdapter.isEmpty()) {
            swipeRefreshLayout.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        }

        progressDialog.dismiss();
    }

}
