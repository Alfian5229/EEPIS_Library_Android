package com.example.eepislibrary.activity.ui.ebook;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.eepislibrary.R;
import com.example.eepislibrary.adapter.ebook.ListEbookAdapter;
import com.example.eepislibrary.adapter.ebook.ListEbookAdapterItems;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EbookFragment extends Fragment implements SearchView.OnQueryTextListener{

    private ArrayList<ListEbookAdapterItems> listEbookData = new ArrayList<>();
    private View view;
    private GridView gridView;
    private TextView tv_ebook_empty;
    private Session session;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String newText;
    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EbookFactory ebookFactory = new EbookFactory(requireActivity().getApplication());
        EbookViewModel ebookViewModel = new ViewModelProvider(this, ebookFactory)
                .get(EbookViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ebook, container, false);

        init(root);
        ebookViewModel.getText().observe(getViewLifecycleOwner(), tv_ebook_empty::setText);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.search_buku).setVisible(true);
    }

    private void init(View view){
        gridView = view.findViewById(R.id.gv_list_ebook);
        tv_ebook_empty = view.findViewById(R.id.tv_ebook_empty);
        session = new Session(getContext());

        swipeRefreshLayout = view.findViewById(R.id.swipe_view_ebook);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            populateEbook(newText);
            swipeRefreshLayout.setRefreshing(false);
        });

        progressDialog = CustomLoading.getInstance(getContext());
        progressDialog.show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        populateEbook(null);
    }

    private void populateEbook(String newText) {
        listEbookData.clear();
        callApiListEbook(newText);
    }

    private void callApiListEbook(String newText) {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = api.getListEbook(session.getIdUser(), session.getToken(), newText);
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
                                initListEbook(jsonObject);
                            }
                            catch (JSONException e){
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            progressDialog.dismiss();
                            if(!jsonObject.getString("reason").equals("Invalid token")){
                                Snackbar.make(view.findViewById(R.id.view_ebook), jsonObject.getString("reason"), Snackbar.LENGTH_LONG)
                                        .show();
                            }
                            else{
                                InvalidToken.backToLogin(getContext(), requireActivity());
                            }
                        }

                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), R.string.system_error, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initListEbook(JSONObject jsonObject) throws JSONException {
        JSONArray data = jsonObject.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);

            listEbookData.add(new ListEbookAdapterItems(
                    object.getString("id"),
                    object.getString("gambar"),
                    object.getString("judul"),
                    object.getString("ebook")
            ));
        }

        ListEbookAdapter listEbookAdapter = new ListEbookAdapter(getContext(), listEbookData);
        gridView.setAdapter(listEbookAdapter);

        if (listEbookAdapter.isEmpty()) {
            swipeRefreshLayout.setVisibility(View.GONE);
            gridView.setVisibility(View.GONE);
            tv_ebook_empty.setVisibility(View.VISIBLE);
        } else {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.VISIBLE);
            tv_ebook_empty.setVisibility(View.GONE);
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
        populateEbook(newText);

        return false;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        SearchView searchView = (SearchView) menu.findItem(R.id.search_buku).getActionView();
        searchView.setQueryHint("Cari...");
        searchView.setOnQueryTextListener(this);
    }

}
