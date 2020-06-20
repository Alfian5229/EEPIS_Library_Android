package com.example.eepislibrary.adapter.list_buku;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eepislibrary.R;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.grid_view_list_buku, parent, false);
            final ListBukuAdapterItems items = listBukuAdapter.get(position);

            ImageView imageView = convertView.findViewById(R.id.list_buku_cover_buku);
            Picasso.get().load(items.getGambar()).into(imageView);

            TextView tv_judul = convertView.findViewById(R.id.list_buku_judul);
            tv_judul.setText(items.getJudul());

            imageView.getLayoutParams().height = (int) (getColumnWidth(context, parent.findViewById(R.id.gv_list_buku)) * 1.6);
        }

        return convertView;
    }

    private int getColumnWidth(Context context, GridView gridView) {
        Resources res = context.getResources();
        int lPad = 16;
        int rPad = 16;
        int hSpace = 16 + 10;
        int cols = gridView.getNumColumns();
        int width = gridView.getWidth();

        return (width - lPad - rPad + hSpace) / cols - hSpace;
    }
}
