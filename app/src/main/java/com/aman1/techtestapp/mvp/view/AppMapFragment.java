package com.aman1.techtestapp.mvp.view;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aman1.techtestapp.R;
import com.aman1.techtestapp.events.ResultsMessageEvent;
import com.aman1.techtestapp.mvp.model.Result;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class AppMapFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "AppMapFragment";

    private List<Result> result = new ArrayList<>();

    private GoogleMap mGoogleMap;

    private ResultsMessageEvent event;

    public static AppMapFragment newInstance() {

        Bundle args = new Bundle();

        AppMapFragment fragment = new AppMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.places_map);
        mapFragment.getMapAsync(this);
        //do stuff
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        UiSettings uiSettings = mGoogleMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setAllGesturesEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);


        if (event != null) {
            if (mGoogleMap != null){
                mGoogleMap.clear();
            }
            result.clear();
            result.addAll(event.getResultMessage());
            placeMarkers(result);
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        event = EventBus.getDefault().getStickyEvent(ResultsMessageEvent.class);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onResultMessageEvent(ResultsMessageEvent event){
        if (mGoogleMap != null){
            mGoogleMap.clear();
        }
        result.addAll(event.getResultMessage());
        placeMarkers(result);
    }

    private void placeMarkers(List<Result> results){
        for (Result result: results) {
            addLocationMarker(result);
        }
    }

    private void addLocationMarker(final Result result) {
        Double lat = result.getGeometry().getLocation().getLat();
        Double lng = result.getGeometry().getLocation().getLng();

        MarkerOptions options = new MarkerOptions()
                .title(result.getName())
                .position(new LatLng(lat,lng))
                .anchor(0.5f, 1.0f)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        mGoogleMap.addMarker(options);

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                prepareIntent(result);
                return false;
            }
        });

    }

    private void prepareIntent(Result result){
        Intent intent  = new Intent(getActivity(), LocationDetailActivity.class);
        if (result.getName() != null){
            intent.putExtra("LOCATION_NAME", result.getName());
        }
        if (result.getFormattedAddress() != null){
            intent.putExtra("LOCATION_ADDRESS", result.getFormattedAddress());
        }
        if (result.getPhotos() != null){
            intent.putExtra("LOCATION_PHOTO_REF", result.getPhotos().get(0).getPhotoReference());
            intent.putExtra("LOCATION_PHOTO_HEIGHT", result.getPhotos().get(0).getHeight());
            intent.putExtra("LOCATION_PHOTO_WIDTH", result.getPhotos().get(0).getWidth());
        }


        startActivity(intent);
    }
}


