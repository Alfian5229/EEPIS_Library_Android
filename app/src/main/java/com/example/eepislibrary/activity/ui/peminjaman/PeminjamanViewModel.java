package com.example.eepislibrary.activity.ui.peminjaman;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eepislibrary.R;

class PeminjamanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    PeminjamanViewModel(Application context) {
        mText = new MutableLiveData<>();
        mText.setValue(context.getString(R.string.empty_peminjaman_message));
    }
    LiveData<String> getText() {
        return mText;
    }
}