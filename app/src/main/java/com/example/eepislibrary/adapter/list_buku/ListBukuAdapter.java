package com.example.eepislibrary.adapter.list_buku;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eepislibrary.R;
import com.example.eepislibrary.activity.ui.detail_buku.DetailBukuActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if(listBukuAdapter.size() == 0){
            return 1;
        }
        return listBukuAdapter.size();
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

            Intent intent = new Intent(context, DetailBukuActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("id_buku", id_buku);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

        return convertView;
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
