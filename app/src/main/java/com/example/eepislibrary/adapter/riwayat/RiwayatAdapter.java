package com.example.eepislibrary.adapter.riwayat;

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

public class RiwayatAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<RiwayatAdapterItems> listRiwayatAdapter;

    public RiwayatAdapter(ArrayList<RiwayatAdapterItems> listnewsDataAdpater, Context context) {
        this.listRiwayatAdapter = listnewsDataAdpater;
        this.context = context;
    }
    @Override
    public int getCount() {
        return listRiwayatAdapter.size();
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
            convertView = layoutInflater.inflate(R.layout.list_view_riwayat, parent, false);
            final RiwayatAdapterItems items = listRiwayatAdapter.get(position);

            ImageView imageView = convertView.findViewById(R.id.riwayat_cover_buku);
            Picasso.get().load(items.getGambar()).into(imageView);

            TextView tv_judul = convertView.findViewById(R.id.tv_riwayat_judul);
            tv_judul.setText(items.getJudul());

            TextView tv_tgl_pinjam = convertView.findViewById(R.id.tv_riwayat_tgl_pinjam);
            tv_tgl_pinjam.setText(String.format("Tanggal Pinjam: %s", items.getTgl_pinjam()));

            TextView tv_tgl_kembai = convertView.findViewById(R.id.tv_riwayat_tgl_kembali);
            tv_tgl_kembai.setText(String.format("Tanggal Kembali: %s", items.getTgl_kembali()));

        }

        return convertView;

    }
}
