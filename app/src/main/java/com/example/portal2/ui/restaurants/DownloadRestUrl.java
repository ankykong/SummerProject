package com.example.portal2.ui.restaurants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadRestUrl {

    public String readUrl(String myUrl) throws IOException {
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(myUrl);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestProperty("x-rapidapi-host", "us-restaurant-menus.p.rapidapi.com");
            urlConnection.setRequestProperty("x-rapidapi-key", "8fae33aec8msh83fef00f566e9fbp173107jsn27c30e0f67fb");
            urlConnection.setRequestMethod("GET");

            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while( (line = br.readLine()) != null ){
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            inputStream.close();
            urlConnection.disconnect();
        }

        return data;
    }

}
