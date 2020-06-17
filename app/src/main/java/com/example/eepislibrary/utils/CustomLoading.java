package com.example.eepislibrary.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.eepislibrary.R;

public class CustomLoading {
    public static ProgressDialog getInstance(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.LoginTheme_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(context.getString(R.string.loading));
        return progressDialog;
    }
}
