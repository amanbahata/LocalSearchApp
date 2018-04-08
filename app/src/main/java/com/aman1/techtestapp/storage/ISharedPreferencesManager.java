package com.aman1.techtestapp.storage;

import com.aman1.techtestapp.mvp.model.Response;

public interface ISharedPreferencesManager {

    void saveLastSearch(Response response);

    Response getLastSearch();

    void clearPreference();
}
