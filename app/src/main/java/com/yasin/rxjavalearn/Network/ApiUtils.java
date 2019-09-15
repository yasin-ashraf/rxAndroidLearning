package com.yasin.rxjavalearn.Network;

/**
 * Created by im_yasinashraf started on 13/7/17.
 */

public class ApiUtils {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public static ApiInterface getServices() {
        return RetrofitClient.getRetrofitClient(BASE_URL).create(ApiInterface.class);
    }
}

