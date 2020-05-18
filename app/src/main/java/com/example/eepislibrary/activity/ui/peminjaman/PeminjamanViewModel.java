package com.example.eepislibrary.activity.ui.peminjaman;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PeminjamanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PeminjamanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    LiveData<String> getText() {
        return mText;
    }
}