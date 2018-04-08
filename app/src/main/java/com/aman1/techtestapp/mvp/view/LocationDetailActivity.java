package com.aman1.techtestapp.mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.aman1.techtestapp.R;
import com.aman1.techtestapp.utils.Utils;

public class LocationDetailActivity extends AppCompatActivity {

    private static final String TAG = "LocationDetailActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        TextView locationName = findViewById(R.id.location_name);
        TextView locationAddress = findViewById(R.id.location_address);
        ImageView image = findViewById(R.id.location_image);

        Intent intent = getIntent();
        String name = intent.getStringExtra("LOCATION_NAME");
        String address = intent.getStringExtra("LOCATION_ADDRESS");


        if (intent.getStringExtra("LOCATION_PHOTO_REF") != null) {
            String photoRef = intent.getStringExtra("LOCATION_PHOTO_REF");
            int photoHeight = intent.getIntExtra("LOCATION_PHOTO_HEIGHT", 0);
            int photoWidth = intent.getIntExtra("LOCATION_PHOTO_WIDTH", 0);

            String imageUrl = Utils.formatPhotoUrl(photoRef, photoHeight, photoWidth);
            Utils.loadImage(imageUrl, image);
        }

        locationName.setText(name);
        locationAddress.setText(address);
    }
}
