package com.example.eepislibrary.api;

import com.example.eepislibrary.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    public static Retrofit getClient() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }
}
