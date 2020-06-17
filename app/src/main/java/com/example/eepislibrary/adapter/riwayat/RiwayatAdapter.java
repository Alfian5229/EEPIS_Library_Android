package com.example.eepislibrary.adapter.riwayat;

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

public class RiwayatAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<RiwayatAdapterItems> listRiwayatAdapter;

    public RiwayatAdapter(ArrayList<RiwayatAdapterItems> listRiwayatAdapter, Context context) {
        this.listRiwayatAdapter = listRiwayatAdapter;
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
            tv_tgl_pinjam.setText(items.getTgl_pinjam());

            TextView tv_tgl_kembali = convertView.findViewById(R.id.tv_riwayat_tgl_kembali);
            tv_tgl_kembali.setText(items.getTgl_kembali());


            Drawable start_date = context.getDrawable(R.drawable.ic_startdate);
            Drawable end_date = context.getDrawable(R.drawable.ic_enddate);

            float density = context.getResources().getDisplayMetrics().density;
            int width = Math.round(24 * density);
            int height = Math.round(24 * density);

            Objects.requireNonNull(start_date).setBounds(0, 0, width, height);
            tv_tgl_pinjam.setCompoundDrawables(start_date, null, null, null);
            tv_tgl_pinjam.setCompoundDrawablePadding(10);
            tv_tgl_pinjam.getCompoundDrawables()[0].setTint(ContextCompat.getColor(context, R.color.color_primary));

            Objects.requireNonNull(end_date).setBounds(0, 0, width, height);
            tv_tgl_kembali.setCompoundDrawables(end_date, null, null, null);
            tv_tgl_kembali.setCompoundDrawablePadding(10);
            tv_tgl_kembali.getCompoundDrawables()[0].setTint(ContextCompat.getColor(context, R.color.color_primary));

        }

        return convertView;

    }
}
