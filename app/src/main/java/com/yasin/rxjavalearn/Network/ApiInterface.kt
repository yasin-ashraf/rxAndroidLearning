package com.yasin.rxjavalearn.Network

import com.yasin.rxjavalearn.nestedFlatMap.Post
import com.yasin.rxjavalearn.networkCallWithRx.Photo
import com.yasin.rxjavalearn.networkCallWithRx.User

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiInterface {

    @get:GET("albums/1/photos/")
    val photos: Observable<List<Photo>>

    @get:GET("users/")
    val users: Observable<List<User>>

    @GET("posts/")
    fun getPosts(@Query("userId") userID: Int): Observable<List<Post>>

    @HEAD
    fun checkIfImageExists(@Url url: String): Call<Void>
}
