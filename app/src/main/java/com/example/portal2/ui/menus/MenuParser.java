package com.example.portal2.ui.menus;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuParser {

    private HashMap<String, String> getPlace(JSONObject restaurantJson){

        HashMap<String, String> restaurantInfoList = new HashMap<>();
        String itemName = "-NA-";
        String itemDescription = "-NA-";
        String price = "-NA-";
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";



        try {
            if (!restaurantJson.isNull(("restaurant_name"))) {
                placeName = restaurantJson.getString("restaurant_name");
            }

            latitude = String.valueOf(restaurantJson.getJSONObject("geo").getDouble("lat"));
            longitude = String.valueOf(restaurantJson.getJSONObject("geo").getDouble("lon"));

            if (!restaurantJson.isNull(("menu_item_name"))) {
                itemName = restaurantJson.getString("menu_item_name");
            }

            if( !restaurantJson.isNull("menu_item_description")) {
                itemDescription = restaurantJson.getString("menu_item_description");
            }

            if(restaurantJson.getJSONArray("menu_item_pricing").length() != 0 ) {
                JSONObject jsonObj = (JSONObject)
                        restaurantJson.getJSONArray("menu_item_pricing")
                                .get(0);
                if( !jsonObj.isNull("priceString")){
                    price = jsonObj.getString("priceString");
                }
            }

            restaurantInfoList.put("itemName", itemName);
            restaurantInfoList.put("itemDescription", itemDescription);
            restaurantInfoList.put("price", price);
            restaurantInfoList.put("vicinity", vicinity);
            restaurantInfoList.put("placeName", placeName);
            restaurantInfoList.put("latitude", latitude);
            restaurantInfoList.put("longitude", longitude);

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
                Log.d("HELPME", "item" + i + ": " + placeMap.toString());
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
