package com.example.eepislibrary.adapter.list_buku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.eepislibrary.R;
import com.example.eepislibrary.api.ApiClient;
import com.example.eepislibrary.api.ApiInterface;
import com.example.eepislibrary.utils.Session;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListBukuAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ListBukuAdapterItems> listBukuAdapter;

    public ListBukuAdapter(Context context, ArrayList<ListBukuAdapterItems> listBukuAdapter) {
        this.context = context;
        this.listBukuAdapter = listBukuAdapter;
    }

    @Override
    public int getCount() {
        return listBukuAdapter.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridView gridView = parent.findViewById(R.id.gv_list_buku);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.grid_view_list_buku, parent, false);
            final ListBukuAdapterItems items = listBukuAdapter.get(position);

            ImageView imageView = convertView.findViewById(R.id.list_buku_cover_buku);
            Picasso.get().load(items.getGambar()).into(imageView);

            TextView tv_judul = convertView.findViewById(R.id.list_buku_judul);
            tv_judul.setText(items.getJudul());

            imageView.getLayoutParams().height = (int) (getColumnWidth(gridView) * 1.3);
        }

        gridView.setOnItemClickListener((parent1, view1, position1, id1) -> {
            String id_buku = listBukuAdapter.get(position1).getId();

            callApiPesan(id_buku, parent);

//            Intent intent = new Intent(context, MainActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString("id_buku", id_buku);
//            intent.putExtras(bundle);
//            context.startActivity(intent);
        });

        return convertView;
    }

    private void callApiPesan(String id_buku, View view) {
        Session session = new Session(context);

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = api.postPosan(session.getIdUser(), session.getToken(), id_buku);
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

    private int getColumnWidth(GridView gridView) {
        int lPad = 16;
        int rPad = 16;
        int hSpace = 16 + 10;
        int cols = gridView.getNumColumns();
        int width = gridView.getWidth();

        return (width - lPad - rPad + hSpace) / cols - hSpace;
    }
}
