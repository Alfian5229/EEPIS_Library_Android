package com.example.eepislibrary.activity.ui.riwayat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

public class RiwayatFactory implements ViewModelProvider.Factory {
    private Application mApplication;


    RiwayatFactory(Application application) {
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return Objects.requireNonNull(modelClass.cast(new RiwayatViewModel(mApplication)));
    }
}
