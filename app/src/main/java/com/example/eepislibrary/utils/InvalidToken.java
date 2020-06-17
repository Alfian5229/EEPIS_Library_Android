package com.example.eepislibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.eepislibrary.R;
import com.example.eepislibrary.activity.LoginActivity;

public class InvalidToken {
    public static void backToLogin(Context context, Activity activity) {
        Session session = new Session(context);
        session.destroySession();
        Toast.makeText(context, R.string.expired_token_message, Toast.LENGTH_LONG).show();
        activity.startActivity(new Intent(context, LoginActivity.class));
        activity.finish();
    }
}
