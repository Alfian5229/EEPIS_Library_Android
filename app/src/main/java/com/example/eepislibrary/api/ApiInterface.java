package com.example.eepislibrary.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

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

    @FormUrlEncoded
    @POST("list_buku")
    Call<String> getListBuku(
            @Header("id_user") String user,
            @Header("token") String token,
            @Field("query") String query
    );

    @FormUrlEncoded
    @POST("list_ebook")
    Call<String> getListEbook(
            @Header("id_user") String user,
            @Header("token") String token,
            @Field("query") String query
    );

    @FormUrlEncoded
    @POST("pesan")
    Call<String> postPosan(
            @Header("id_user") String user,
            @Header("token") String token,
            @Field("id_buku") String id_buku
    );

    @GET("list_pesan")
    Call<String> getListPesan(
            @Header("id_user") String user,
            @Header("token") String token
    );

    @GET("list_pinjam")
    Call<String> getListPinjam(
            @Header("id_user") String user,
            @Header("token") String token
    );

    @GET("riwayat")
    Call<String> getRiwayat(
            @Header("id_user") String user,
            @Header("token") String token
    );
}
