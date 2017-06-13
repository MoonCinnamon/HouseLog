package com.cinnamon.moon.houselog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by moonp on 2017-06-13.
 */

public class Controlair extends AsyncTask<String, String, String> {
    private static String TAG = "phptest_MainActivity";
    private Context context;
    private MainListViewAdapter adapter;
    public Controlair(Context applicationContext, MainListViewAdapter adapter) {
        context = applicationContext;
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String value = (String) params[0];
        Log.d("data", value);
        String serverURL = "http://27.117.243.58/aircon.php";
        String postParameters = "value=" + value;

        try {

            URL url = new URL(serverURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            //httpURLConnection.setRequestProperty("content-type", "application/json");
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();


            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(postParameters.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();


            int responseStatusCode = httpURLConnection.getResponseCode();
            return value;
        } catch (Exception e) {

            Log.d(TAG, "InsertData: Error ", e);
            return new String("Error: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        savePreferences(result);
        if (result.equals("1"))
            adapter.set(null, context.getResources().getString(R.string.house), context.getResources().getString(R.string.aircon), false);
        else
            adapter.set(null, context.getResources().getString(R.string.house), context.getResources().getString(R.string.aricon_d), false);
        adapter.notifyDataSetChanged();
    }

    private void savePreferences(String value){
        SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("air", Integer.valueOf(value));
        editor.commit();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}

