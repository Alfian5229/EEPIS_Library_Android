package com.example.eepislibrary.activity.ui.buku;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eepislibrary.R;

public class BukuViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BukuViewModel(Application context) {
        mText = new MutableLiveData<>();
        mText.setValue(context.getString(R.string.empty_buku_message));
    }

    LiveData<String> getText() {
        return mText;
    }
}