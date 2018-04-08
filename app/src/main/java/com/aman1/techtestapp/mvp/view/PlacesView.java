package com.aman1.techtestapp.mvp.view;

import com.aman1.techtestapp.mvp.model.Result;

import java.util.List;

public interface PlacesView {
    void onSearchSuccess(List<Result> results);
    void onSearchFail(String message);
}
