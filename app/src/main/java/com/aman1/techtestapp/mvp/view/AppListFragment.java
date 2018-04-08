package com.aman1.techtestapp.mvp.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aman1.techtestapp.R;
import com.aman1.techtestapp.events.ResultsMessageEvent;
import com.aman1.techtestapp.mvp.model.Result;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class AppListFragment extends Fragment {
    private static final String TAG = "AppListFragment";

    private RecyclerView placesRecyclerView;
    private PlacesAdapter placesAdapter;

    public static AppListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AppListFragment fragment = new AppListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        placesRecyclerView = view.findViewById(R.id.places_list);
        placesAdapter = new PlacesAdapter(inflater);
        placesRecyclerView.setAdapter(placesAdapter);
        placesRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onResultMessageEvent(ResultsMessageEvent event){
        List<Result> resultMessage = event.getResultMessage();

        placesAdapter.clearAdapter();
        placesAdapter.addPlaces(resultMessage);
    }
}
