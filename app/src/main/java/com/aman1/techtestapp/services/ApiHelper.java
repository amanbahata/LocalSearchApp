package com.aman1.techtestapp.services;

import com.aman1.techtestapp.mvp.model.Response;

import io.reactivex.Observable;

public interface ApiHelper {

    Observable<Response> getPlace(String placeName, String apiKey);

}
