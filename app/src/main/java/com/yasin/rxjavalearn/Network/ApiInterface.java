package com.yasin.rxjavalearn.Network;

import com.yasin.rxjavalearn.networkCallWithRx.Photo;
import com.yasin.rxjavalearn.networkCallWithRx.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET("albums/1/photos/")
    Observable<List<Photo>> getPhotos();

    @GET("users/")
    Observable<List<User>> getUsers();

    @HEAD()
    Call<Void> checkIfImageExists(@Url String url);
}
