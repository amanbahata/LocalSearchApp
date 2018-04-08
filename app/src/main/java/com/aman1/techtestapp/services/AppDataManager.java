package com.aman1.techtestapp.services;

import com.aman1.techtestapp.mvp.model.Response;

import io.reactivex.Observable;


public class AppDataManager implements ApiHelper {

    private RequestInterface requestInterface;

    public AppDataManager() {
        requestInterface = ServerConnection.getServerConnection();
    }

    @Override
    public Observable<Response> getPlace(String placeName, String apiKey) {
        return requestInterface.getPlace(placeName, apiKey);
    }

}
