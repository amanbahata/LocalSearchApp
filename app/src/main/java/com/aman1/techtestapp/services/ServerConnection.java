package com.aman1.techtestapp.services;

import com.aman1.techtestapp.constants.ApiList;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerConnection {

    private static Retrofit retrofit;


    public static RequestInterface getServerConnection(){




        retrofit = new Retrofit.Builder()
                .baseUrl(ApiList.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(RequestInterface.class);
    }
}
