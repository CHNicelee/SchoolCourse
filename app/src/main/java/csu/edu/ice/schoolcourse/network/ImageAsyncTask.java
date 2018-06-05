package csu.edu.ice.schoolcourse.network;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 * Created by ice on 2018/6/2.
 */

public abstract class ImageAsyncTask extends AsyncTask<String,Integer,Bitmap> {
    @Override
    protected Bitmap doInBackground(String... strings) {
        if(strings.length==0)return null;
        Bitmap result = NetUtils.getBitmapResult(strings[0]);
        return result;
    }

    @Override
    public abstract void onPostExecute(Bitmap news);
}
