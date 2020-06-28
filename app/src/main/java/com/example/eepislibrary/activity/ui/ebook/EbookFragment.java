package com.example.eepislibrary.activity.ui.ebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.eepislibrary.R;

public class EbookFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EbookFactory ebookFactory = new EbookFactory(requireActivity().getApplication());
        EbookViewModel ebookViewModel = new ViewModelProvider(this, ebookFactory)
                .get(EbookViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ebook, container, false);

        TextView tvEmpty = root.findViewById(R.id.tv_ebook_empty);
        ebookViewModel.getText().observe(getViewLifecycleOwner(), tvEmpty::setText);
        return root;
    }

}
