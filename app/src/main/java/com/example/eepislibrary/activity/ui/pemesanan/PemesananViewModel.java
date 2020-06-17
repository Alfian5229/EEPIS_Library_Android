package com.example.eepislibrary.activity.ui.pemesanan;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eepislibrary.R;

class PemesananViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    PemesananViewModel(Application context) {
        mText = new MutableLiveData<>();
        mText.setValue(context.getString(R.string.empty_pemesanan_message));
    }

    LiveData<String> getText() {
        return mText;
    }
}