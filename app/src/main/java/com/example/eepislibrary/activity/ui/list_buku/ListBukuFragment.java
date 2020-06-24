package com.example.eepislibrary.activity.ui.list_buku;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.eepislibrary.R;

public class ListBukuFragment extends Fragment{

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ListBukuViewModel listBukuViewModel = new ViewModelProvider(this).get(ListBukuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        listBukuViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }
}
