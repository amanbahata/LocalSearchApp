package com.aman1.techtestapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.ImageView;

import com.aman1.techtestapp.constants.ApiList;
import com.aman1.techtestapp.constants.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Utils {

    public static String formatPhotoUrl(String photoRef, int photoHeight, int photoWidth){

        return String.format(ApiList.IMAGE_URL, photoRef, String.valueOf(photoHeight), String.valueOf(photoWidth), Constants.GOOGLE_PLACES_API_KEY);
    }

    public static void loadImage(String imageUrl, ImageView view) {
            Picasso.get()
                    .load(imageUrl)
                    .fit()
                    .into(view);
    }

    public static String flattenTypesArray(List<String> list){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size() - 1; i++) {
            sb.append((list.get(i).replace("_", " ")));
            sb.append(", ");
        }
        sb.append((list.get(list.size() - 1).replace("_", " ")));
        sb.append(".");
        return sb.toString();
    }

    public static boolean isConnectedNetwork (Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo () != null && cm.getActiveNetworkInfo ().isConnectedOrConnecting ();
    }

}
