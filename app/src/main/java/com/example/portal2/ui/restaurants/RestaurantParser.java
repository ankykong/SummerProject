package com.example.portal2.ui.restaurants;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RestaurantParser {

    private HashMap<String, String> getPlace(JSONObject restaurantJson){

        HashMap<String, String> restaurantInfoList = new HashMap<>();
        String hours = "-NA-";
        String restaurant_id = "-NA-";
        String restaurantName = "-NA-";
        String address;
        String latitude = "";
        String longitude = "";



        try {
            if (!restaurantJson.isNull(("restaurant_name"))) {
                restaurantName = restaurantJson.getString("restaurant_name");
            }

            if( !restaurantJson.isNull("hours")) {
                hours = restaurantJson.getString("hours");
            }

            if( !restaurantJson.isNull("restaurant_id")) {
                restaurant_id = String.valueOf(restaurantJson.getInt("restaurant_id"));
            }

            address = restaurantJson.getJSONObject("address").getString("formatted");

            latitude = restaurantJson.getJSONObject("geo").getString("lat");
            longitude = restaurantJson.getJSONObject("geo").getString("lon");

            restaurantInfoList.put("restaurant_name", restaurantName);
            restaurantInfoList.put("lat", latitude);
            restaurantInfoList.put("lng", longitude);
            restaurantInfoList.put("hours", hours);
            restaurantInfoList.put("address", address);
            restaurantInfoList.put("restaurant_id", restaurant_id);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return restaurantInfoList;

    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray){

        int count = jsonArray.length();
        List<HashMap<String, String>> placesList = new ArrayList<>();
        HashMap<String, String> placeMap = null;

        for( int i = 0; i < count; ++i){

            try{
                placeMap = getPlace((JSONObject)jsonArray.get(i));
                placesList.add(placeMap);
            } catch(JSONException e){
                e.printStackTrace();
            }

        }

        return placesList;

    }

    public List<HashMap<String, String>> parse(String jsonData){

        JSONArray jsonArray = null;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonData);
            jsonObject = jsonObject.getJSONObject("result");
            jsonArray = jsonObject.getJSONArray("data");
            Log.i("HELP ME", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("HELP", jsonData);

        return getPlaces(jsonArray);

    }

}
