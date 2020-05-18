package com.example.eepislibrary.activity.ui.pemesanan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.eepislibrary.R;

public class PemesananFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PemesananViewModel pemesananViewModel = new ViewModelProvider(this).get(PemesananViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pemesanan, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        pemesananViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
}
