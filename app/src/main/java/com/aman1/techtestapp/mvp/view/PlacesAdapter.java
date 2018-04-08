package com.aman1.techtestapp.mvp.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aman1.techtestapp.R;
import com.aman1.techtestapp.mvp.model.Result;
import com.aman1.techtestapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder> {

    private final List<Result> mPlaces = new ArrayList<>();
    private final LayoutInflater mLayoutInflater;

    public PlacesAdapter(LayoutInflater inflater) {
        mLayoutInflater = inflater;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlaceViewHolder(mLayoutInflater.inflate(R.layout.place_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        holder.name.setText(mPlaces.get(position).getName());
        holder.address.setText(mPlaces.get(position).getFormattedAddress());
        holder.type.setText(Utils.flattenTypesArray(mPlaces.get(position).getTypes()));

        if (mPlaces.get(position).getPhotos() != null) {
            String ref = mPlaces.get(position).getPhotos().get(0).getPhotoReference();
            int width = mPlaces.get(position).getPhotos().get(0).getWidth();
            int height = mPlaces.get(position).getPhotos().get(0).getHeight();

            String url = Utils.formatPhotoUrl(ref, height, width);
            Utils.loadImage(url, holder.icon);
        }
    }


    @Override
    public int getItemCount() {
        return mPlaces.size();
    }


    public void addPlaces(List<Result> places) {
        int size = mPlaces.size();
        mPlaces.addAll(places);
        notifyItemRangeInserted(size, places.size());
    }

    public void clearAdapter() {
        int size = mPlaces.size();
        mPlaces.clear();
        notifyItemRangeRemoved(0, size);
    }

    class PlaceViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView name;
        private TextView address;
        private TextView type;

        PlaceViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.place_icon);
            name = itemView.findViewById(R.id.place_name);
            address = itemView.findViewById(R.id.place_address);
            type = itemView.findViewById(R.id.place_type);
        }

    }
}
