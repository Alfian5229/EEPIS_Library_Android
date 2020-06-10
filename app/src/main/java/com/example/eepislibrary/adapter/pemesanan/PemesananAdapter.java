package com.example.eepislibrary.adapter.pemesanan;

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

public class PemesananAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PemesananAdapterItems> listPemesananAdapter;

    public PemesananAdapter(ArrayList<PemesananAdapterItems> listPemesananAdapter, Context context) {
        this.listPemesananAdapter = listPemesananAdapter;
        this.context = context;
    }
    @Override
    public int getCount() {
        return listPemesananAdapter.size();
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
            convertView = layoutInflater.inflate(R.layout.list_view_pemesanan, parent, false);
            final PemesananAdapterItems items = listPemesananAdapter.get(position);

            ImageView imageView = convertView.findViewById(R.id.pemesanan_cover_buku);
            Picasso.get().load(items.getGambar()).into(imageView);

            TextView tv_judul = convertView.findViewById(R.id.tv_pemesanan_judul);
            tv_judul.setText(items.getJudul());

            TextView tv_tgl_pesan = convertView.findViewById(R.id.tv_pemesanan_tgl_pesan);
            tv_tgl_pesan.setText(String.format("Tanggal Pesan: %s", items.getTgl_pesan()));

            TextView tv_batas_waktu = convertView.findViewById(R.id.tv_pemesanan_batas_waktu);
            tv_batas_waktu.setText(String.format("Tanggal Batas Pengambilan: %s", items.getBatas_waktu()));

        }

        return convertView;

    }
}
