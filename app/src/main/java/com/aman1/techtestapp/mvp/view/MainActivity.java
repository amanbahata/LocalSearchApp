package com.aman1.techtestapp.mvp.view;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aman1.techtestapp.R;
import com.aman1.techtestapp.events.ResultsMessageEvent;
import com.aman1.techtestapp.mvp.model.Result;
import com.aman1.techtestapp.mvp.presenter.PlacesPresenter;
import com.aman1.techtestapp.services.AppDataManager;
import com.aman1.techtestapp.storage.SharedPreferencesManager;
import com.aman1.techtestapp.utils.Utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PlacesView {

    private static final String TAG = "MainActivity";

    private PlacesPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().findItem(R.id.actionList).setChecked(true);


        presenter = new PlacesPresenter(this, new AppDataManager(), new SharedPreferencesManager(getSharedPreferences("techtestapp.aman1.com.activities", Context.MODE_PRIVATE)));

        handleFragment(AppListFragment.newInstance());

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                presenter.searchPlace(Utils.isConnectedNetwork(getApplicationContext()), query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void handleFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, "TAG")
                .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.actionMap:
                    handleFragment(AppMapFragment.newInstance());
                    return true;
                case R.id.actionList:
                    handleFragment(AppListFragment.newInstance());
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onSearchSuccess(List<Result> results) {
        EventBus.getDefault().postSticky(new ResultsMessageEvent(results));
    }

    @Override
    public void onSearchFail(String message) {
        Log.i(TAG, "onSearchFail: " + message);

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter = null;
    }
}
