package com.vladmikh.projects.exchange_rates.utils;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {

    private static final String BASE_URL ="https://www.cbr-xml-daily.ru/daily_json.js";

    /*
    The method returns data received from the Network
     */
    public static JSONObject getJSONFromNetwork () {
        JSONObject result = null;
        URL url = null;
        try {
            url = new URL(BASE_URL);
            result = new JSONLoadTask().execute(url).get();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }



    private static class JSONLoadTask extends AsyncTask<URL, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result = null;

            if (urls == null || urls.length == 0) {
                return result;
            }
            HttpURLConnection urlConection = null;
            try {
                urlConection = (HttpURLConnection) urls[0].openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConection.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String line = reader.readLine();
                while(line != null) {
                    builder.append(line);
                    line = reader.readLine();
                }
                result = new JSONObject(builder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConection != null) {
                    urlConection.disconnect();
                }
            }
            return result;
        }
    }


}
