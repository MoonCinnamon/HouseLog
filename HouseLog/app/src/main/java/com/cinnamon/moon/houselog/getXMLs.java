package com.cinnamon.moon.houselog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by moonp on 2017-06-13.
 */

public class getXMLs extends AsyncTask<String, String, String[][]> {
    private MainListViewAdapter adapter;
    private Context context;

    public getXMLs(Context applicationContext, MainListViewAdapter adapter) {
        this.adapter = adapter;
        context = applicationContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String[][] doInBackground(String... params) {
        String[][] array = new String[2][4];
        try {
            Document document = Jsoup.connect("http://27.117.243.58/sky2017-6-13-6-1-38.xml")
                    .userAgent("Chrome").get();//rss 데이터 저장.
            Elements elements = document.select("data");//item tag의 내용물 저장.
            for (Element element : elements) {//elements에서 각각의 element마다 동 수행
                array[0][0] = element.getElementsByTag("temp").iterator().next().text();
                array[0][1] = element.getElementsByTag("wfEn").iterator().next().text();
                array[0][2] = element.getElementsByTag("pop").iterator().next().text();
                array[0][3] = element.getElementsByTag("reh").iterator().next().text();
            }
            Document document1 = Jsoup.connect("http://27.117.243.58/Arduino2017-6-13-6-38-21.xml").userAgent("Chrome").get();
            Elements elements1 = document1.select("data");//item tag의 내용물 저장.
            for (Element elementss : elements1) {//elements에서 각각의 element마다 동 수행
                array[1][0] = elementss.getElementsByTag("temp").iterator().next().text();
                array[1][1] = elementss.getElementsByTag("humi").iterator().next().text();
                array[1][2] = elementss.getElementsByTag("thi").iterator().next().text();
            }

            Log.d("동작중","");
            // Message message = handler.obtainMessage();
            // handler.sendMessage(message);// 헨들러를 통해서 메인 스레드 제 신호 전달.
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    @Override
    protected void onPostExecute(String[][] result) {
        super.onPostExecute(result);
        adapter.addItem(null, context.getResources().getString(R.string.today_w) + " : " + result[0][1], "예보", true);
        adapter.addItem(null, context.getResources().getString(R.string.temp) + " : " + result[0][0], "", true);
        adapter.addItem(null, context.getResources().getString(R.string.rain) + " : " + result[0][2], "", true);
        adapter.addItem(null, context.getResources().getString(R.string.reh) + " : " + result[0][3],"", true);
        adapter.addItem(null, "", "", true);
        if (getPreferences() == 0)
            adapter.addItem(null, context.getResources().getString(R.string.house), context.getResources().getString(R.string.aricon_d), false);
        else
            adapter.addItem(null, context.getResources().getString(R.string.house), context.getResources().getString(R.string.aircon), false);
        adapter.addItem(null, context.getResources().getString(R.string.temp) + " : " + result[1][0], "", true);
        adapter.addItem(null, context.getResources().getString(R.string.reh) + " : " + result[1][1], "", true);
        adapter.addItem(null, context.getResources().getString(R.string.thi) + " : " + result[1][2], "", true);
        adapter.notifyDataSetChanged();
    }

    private int getPreferences() {
        SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        return pref.getInt("air", 0);
    }
    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
