package com.aman1.techtestapp.services;

import com.aman1.techtestapp.constants.ApiList;
import com.aman1.techtestapp.mvp.model.Response;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestInterface {


    @GET(ApiList.SEARCH)
    Observable<Response> getPlace(@Query("query") String placeName, @Query("key") String apiKey);

}
