package com.example.portal2.ui.menus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.portal2.R;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    LinearLayout menuList;
    public ArrayList<String> itemButtonArrayList;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);
        menuList = findViewById(R.id.menu_list);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        itemButtonArrayList = intent.getStringArrayListExtra("list");

        GetMenuData getMenuData = new GetMenuData(MenuActivity.this);
        String url = getMenuUrl(id);
        getMenuData.execute(url);


    }

    private String getMenuUrl(int id){

        StringBuilder url = new StringBuilder("https://us-restaurant-menus.p.rapidapi.com/restaurant/");
        url.append(id);
        url.append("/menuitems");

        return url.toString();
    }

}
