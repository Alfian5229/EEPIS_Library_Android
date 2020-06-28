package com.example.eepislibrary.activity.ui.ebook;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eepislibrary.R;

public class EbookViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    EbookViewModel(Application context) {
        mText = new MutableLiveData<>();
        mText.setValue(context.getString(R.string.empty_ebook_message));
    }

    LiveData<String> getText() {
        return mText;
    }
}
