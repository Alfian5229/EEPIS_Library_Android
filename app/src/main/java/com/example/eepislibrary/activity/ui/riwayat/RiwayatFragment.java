package com.example.eepislibrary.activity.ui.riwayat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.eepislibrary.R;

public class RiwayatFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RiwayatFactory riwayatFactory = new RiwayatFactory(requireActivity().getApplication());
        RiwayatViewModel riwayatViewModel = new ViewModelProvider(this, riwayatFactory)
                .get(RiwayatViewModel.class);
        View root = inflater.inflate(R.layout.fragment_riwayat, container, false);
        final TextView textView = root.findViewById(R.id.tv_riwayat_empty);
        riwayatViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
}
