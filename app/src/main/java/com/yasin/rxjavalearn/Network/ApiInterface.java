package com.yasin.rxjavalearn.Network;

import com.yasin.rxjavalearn.NetworkCallWithRx.Photo;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HEAD;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/photos")
    Call<List<Photo>> getPhotos();

    @HEAD()
    Call<Void> checkIfImageExists(@Url String url);
}
