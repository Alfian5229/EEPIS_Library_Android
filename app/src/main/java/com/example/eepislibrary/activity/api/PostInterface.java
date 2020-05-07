package com.example.eepislibrary.activity.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostInterface {

    String JSONURL = "http://192.168.1.6:8000/api/";
    @FormUrlEncoded
    @POST("login")
    Call<String> postLogin(
            @Field("email") String email,
            @Field("password") String password
    );

}
