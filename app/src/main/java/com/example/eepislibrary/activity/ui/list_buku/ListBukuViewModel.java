package com.example.eepislibrary.activity.ui.list_buku;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListBukuViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListBukuViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Ini adalah halaman utama");
    }

    LiveData<String> getText() {
        return mText;
    }
}