package csu.edu.ice.schoolcourse.network;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import csu.edu.ice.schoolcourse.bean.News;
import csu.edu.ice.schoolcourse.utils.MyGson;

/**
 * Created by ice on 2018/6/2.
 */

public abstract class NewsAsyncTask extends AsyncTask<String,Integer,List<News>> {
    private static final String TAG = NewsAsyncTask.class.getSimpleName();

    @Override
    protected List<News> doInBackground(String... strings) {
        if(strings.length==0)return null;
        String result = NetUtils.getStringResult(strings[0]);
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject resultObject = jsonObject.getJSONObject("result");
            JSONArray dataObject = resultObject.getJSONArray("data");

            MyGson gson = new MyGson();
            List<News> newsList = gson.fromJsonArray(dataObject, News.class);
            Log.d(TAG, "doInBackground: "+newsList);
            /*for (int i = 0; i < dataObject.length(); i++) {
                *//*JSONObject newsObject = dataObject.getJSONObject(i);
                News news = new News();

                news.setTitle(newsObject.getString("title"));
                news.setAuthor_name(newsObject.getString("author_name"));
                news.setCategory(newsObject.getString("category"));
                news.setDate(newsObject.getString("date"));
                news.setThumbnail_pic_s(newsObject.get());*//*
                News news = gson.fromString(dataObject.get(i), News.class);
                newsList.add(news);
            }*/
            return newsList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public abstract void onPostExecute(List<News> news);
}
