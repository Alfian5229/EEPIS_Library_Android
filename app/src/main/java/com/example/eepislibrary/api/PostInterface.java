package com.example.eepislibrary.api;

import com.example.eepislibrary.BuildConfig;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostInterface {

    String JSONURL = BuildConfig.SERVER_URL;

    @FormUrlEncoded
    @POST("login")
    Call<String> postLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("cek_token")
    Call<String> postCekToken(
            @Field("email") String email,
            @Field("token") String token
    );

}
