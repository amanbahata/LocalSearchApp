package com.aman1.techtestapp.storage;


import android.content.SharedPreferences;
import android.text.TextUtils;

import com.aman1.techtestapp.mvp.model.Response;
import com.google.gson.Gson;

public class SharedPreferencesManager implements ISharedPreferencesManager {

    private final SharedPreferences preferences;
    private final Gson gson = new Gson();
    private final String PREF_KEY = "KEY_LAST_SEARCH";

    public SharedPreferencesManager(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public void saveLastSearch(Response response) {
        preferences.edit().putString(PREF_KEY, gson.toJson(response)).apply();
    }

    @Override
    public Response getLastSearch() {
        Response response = null;
        String userDetail = preferences.getString(PREF_KEY, "");
        if (!TextUtils.isEmpty(userDetail)) {
            response = gson.fromJson(userDetail, Response.class);
        }
        return response;
    }

    @Override
    public void clearPreference() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
