package com.example.portal2.ui.menus;

import android.os.AsyncTask;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.portal2.R;
import com.example.portal2.ui.maps.MapsActivity;
import com.example.portal2.ui.restaurants.DownloadRestUrl;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

public class GetMenuData extends AsyncTask<Object, String, String> {

    private WeakReference<MenuActivity> mWeakReference;
    MenuActivity activity;

    public GetMenuData(MenuActivity context)
    {
        mWeakReference = new WeakReference<>(context);
        activity = mWeakReference.get();
    }

    @Override
    protected String doInBackground(Object... objects) {
        String url = (String) objects[0];
        String menuData = "";
        DownloadRestUrl downloadUrl = new DownloadRestUrl();
        try {
            menuData = downloadUrl.readUrl(url);
            Log.i("HELP", menuData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return menuData;
    }

    @Override
    protected void onPostExecute(String s){
        List<HashMap<String,String>> menu;
        MenuParser parser = new MenuParser();
        menu = parser.parse(s);

        for(int i = 0; i < menu.size(); ++i) {
            HashMap<String, String> menuItem = menu.get(i);
            final int temp = i;
            Button itemI = new Button(activity);
            StringBuilder sb = new StringBuilder();
            sb.append(menuItem.get("itemName"));
            sb.append('\n');
            sb.append(menuItem.get("itemDescription"));
            sb.append('\n');
            sb.append(menuItem.get("price"));
            itemI.setText(sb.toString());
            activity.itemButtonArrayList.add(itemI.getText().toString());
            LinearLayout menuList = activity.findViewById(R.id.menu_list);
            menuList.addView(itemI);
        }
    }

}
