package com.example.eepislibrary.activity.ui.pemesanan;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

public class PemesananFactory implements ViewModelProvider.Factory {
    private Application mApplication;

    PemesananFactory(Application application) {
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return Objects.requireNonNull(modelClass.cast(new PemesananViewModel(mApplication)));
    }
}