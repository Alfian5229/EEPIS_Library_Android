package com.example.eepislibrary.activity.ui.riwayat;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eepislibrary.R;

class RiwayatViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    RiwayatViewModel(Application context) {
        mText = new MutableLiveData<>();
        mText.setValue(context.getString(R.string.empty_riwayat_message));
    }

    LiveData<String> getText() {
        return mText;
    }
}