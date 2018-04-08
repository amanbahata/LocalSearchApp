package com.aman1.techtestapp.mvp.presenter;


import com.aman1.techtestapp.constants.Constants;
import com.aman1.techtestapp.mvp.model.Response;
import com.aman1.techtestapp.mvp.model.Result;
import com.aman1.techtestapp.mvp.view.PlacesView;
import com.aman1.techtestapp.services.ApiHelper;
import com.aman1.techtestapp.storage.ISharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class PlacesPresenter {

    private static final String TAG = "PlacesPresenter";
    private PlacesView view;
    private ApiHelper dataManager;
    private ISharedPreferencesManager preferences;

    public PlacesPresenter(PlacesView view, ApiHelper apiHelper, ISharedPreferencesManager preferences) {
        this.view = view;
        this.dataManager = apiHelper;
        this.preferences = preferences;
    }

    public void searchPlace(boolean isConnected, String name){
        if(!isConnected){
            Response response = preferences.getLastSearch();
            if (response != null) {
                view.onSearchSuccess(response.getResults());
            }
        }else {
            getPlace(name);
        }
    }


    private void getPlace(String placeName){
        dataManager.getPlace(placeName, Constants.GOOGLE_PLACES_API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response response) {
                        preferences.saveLastSearch(response);
                        view.onSearchSuccess(response.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onSearchFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
