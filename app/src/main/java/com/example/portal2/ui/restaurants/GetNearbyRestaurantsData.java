package com.example.portal2.ui.restaurants;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.portal2.R;
import com.example.portal2.ui.maps.MapsActivity;
import com.example.portal2.ui.menus.MenuActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetNearbyRestaurantsData extends AsyncTask<Object, String, String> {

    private WeakReference<MapsActivity> mWeakReference;
    MapsActivity activity;
    ArrayList<String> itemList = new ArrayList<>();

    public GetNearbyRestaurantsData(MapsActivity context)
    {
        mWeakReference = new WeakReference<>(context);
        activity = mWeakReference.get();
    }

    String googlePlacesData;
    GoogleMap mMap;
    String url;
    List<HashMap<String, String>> nearbyRestaurantList = null;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];
        DownloadRestUrl downloadUrl = new DownloadRestUrl();
        Log.i("HELP", url);

        try {
            googlePlacesData = downloadUrl.readUrl(url);
            Log.i("HELP", googlePlacesData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s){

        Log.i("HELP", s);
        RestaurantParser parser = new RestaurantParser();
        nearbyRestaurantList = parser.parse(s);
        showNearbyRestaurants();
    }

    private void showNearbyRestaurants(){

        for(int i = 0; i < nearbyRestaurantList.size(); ++i){

            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyRestaurantList.get(i);

            String placeName = googlePlace.get("restaurant_name");
            String address = googlePlace.get("address");
            String hours = googlePlace.get("hours");
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName +"\n"+ address +"\n"+ hours);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        }

    }

    public int listSize(){
        if(nearbyRestaurantList == null)
            return 0;
        return nearbyRestaurantList.size();
    }

    public int getRestId(int i){
        return Integer.parseInt(nearbyRestaurantList.get(i).get("restaurant_id"));
    }

    public void listNearbyRestaurants(){
        for(int i = 0; i < nearbyRestaurantList.size(); i++) {
            HashMap<String, String> googlePlace = nearbyRestaurantList.get(i);
            Button placei = new Button(activity);
            placei.setId(i);
            final int temp = getRestId(i);
            placei.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, MenuActivity.class);
                    intent.putExtra("id", temp);
                    intent.putStringArrayListExtra("list", itemList);
                    Log.d("HELP", intent.getParcelableArrayListExtra("list").toString());
                    activity.startActivity(intent);
                }
            });
            StringBuilder sb = new StringBuilder();
            sb.append(googlePlace.get("restaurant_name"));
            sb.append('\n');
            sb.append(googlePlace.get("address"));
            sb.append('\n');
            sb.append(googlePlace.get("hours"));
            placei.setText(sb.toString());
            itemList.add(placei.getText().toString());
            activity.buttonArrayList.add(placei);
            activity.restaurantList.addView(placei);
            Log.i("HELP", "Went in here");
        }
    }
}
