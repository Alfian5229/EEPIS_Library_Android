package com.example.eepislibrary.adapter.peminjaman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eepislibrary.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PeminjamanAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PeminjamanAdapterItems> listPeminjamanAdapter;

    public PeminjamanAdapter(ArrayList<PeminjamanAdapterItems> listPeminjamanAdapter, Context context) {
        this.listPeminjamanAdapter = listPeminjamanAdapter;
        this.context = context;
    }
    @Override
    public int getCount() {
        return listPeminjamanAdapter.size();
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
            convertView = layoutInflater.inflate(R.layout.list_view_peminjaman, parent, false);
            final PeminjamanAdapterItems items = listPeminjamanAdapter.get(position);

            ImageView imageView = convertView.findViewById(R.id.peminjaman_cover_buku);
            Picasso.get().load(items.getGambar()).into(imageView);

            TextView tv_judul = convertView.findViewById(R.id.tv_peminjaman_judul);
            tv_judul.setText(items.getJudul());

            TextView tv_tgl_pinjam = convertView.findViewById(R.id.tv_peminjaman_tgl_pinjam);
            tv_tgl_pinjam.setText(String.format("Tanggal Pinjam: %s", items.getTgl_pinjam()));

            TextView tv_batas_waktu = convertView.findViewById(R.id.tv_peminjaman_batas_waktu);
            tv_batas_waktu.setText(String.format("Tanggal Batas Pengembalian: %s", items.getBatas_waktu()));

        }

        return convertView;
    }

}
