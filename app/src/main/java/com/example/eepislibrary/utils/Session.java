package com.example.eepislibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

public class Session {

    private SharedPreferences sharedPreferences;

    public Session(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setSession(String email, String name, String token) {
        sharedPreferences.edit().putString("email", email).apply();
        sharedPreferences.edit().putString("name", name).apply();
        sharedPreferences.edit().putString("token", token).apply();
    }

    public String getEmail() {
        return sharedPreferences.getString("email", "");
    }

    public String getName() {
        return sharedPreferences.getString("name", "");
    }

    public String getToken() {
        return sharedPreferences.getString("token", "empty");
    }

    public void destroySession(){
        sharedPreferences.edit().clear().apply();
    }

}
