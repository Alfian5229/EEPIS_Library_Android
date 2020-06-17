package com.example.eepislibrary.adapter.pemesanan;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.eepislibrary.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

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
            tv_tgl_pesan.setText(items.getTgl_pesan());

            TextView tv_batas_waktu = convertView.findViewById(R.id.tv_pemesanan_batas_waktu);
            tv_batas_waktu.setText(items.getBatas_waktu());


            Drawable start_date = context.getDrawable(R.drawable.ic_startdate);
            Drawable end_date = context.getDrawable(R.drawable.ic_enddate);

            float density = context.getResources().getDisplayMetrics().density;
            int width = Math.round(24 * density);
            int height = Math.round(24 * density);

            Objects.requireNonNull(start_date).setBounds(0, 0, width, height);
            tv_tgl_pesan.setCompoundDrawables(start_date, null, null, null);
            tv_tgl_pesan.setCompoundDrawablePadding(10);
            tv_tgl_pesan.getCompoundDrawables()[0].setTint(ContextCompat.getColor(context, R.color.color_primary));

            Objects.requireNonNull(end_date).setBounds(0, 0, width, height);
            tv_batas_waktu.setCompoundDrawables(end_date, null, null, null);
            tv_batas_waktu.setCompoundDrawablePadding(10);
            tv_batas_waktu.getCompoundDrawables()[0].setTint(ContextCompat.getColor(context, R.color.color_primary));
        }

        return convertView;

    }
}
