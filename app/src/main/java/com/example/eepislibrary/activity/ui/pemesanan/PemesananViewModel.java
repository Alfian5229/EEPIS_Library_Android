package com.example.eepislibrary.activity.ui.pemesanan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PemesananViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PemesananViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    LiveData<String> getText() {
        return mText;
    }
}