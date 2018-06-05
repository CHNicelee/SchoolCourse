package csu.edu.ice.schoolcourse.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ice on 2018/6/2.
 */

public class NetUtils {

    private static final String TAG = "NetUtils";

    private static InputStream getInputStreamFromUrl(@NonNull String path){
        if(path == null || path.equals("")) throw new IllegalArgumentException("path can't be null");
        Log.d(TAG, "getInputStreamFromUrl: path:"+path);
        URL url = null;
        try {
            url = new URL(path);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setRequestProperty("method","GET");
            InputStream is = conn.getInputStream();
            return is;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringResult(@NonNull String path){
        if(path == null || path.equals("")) throw new IllegalArgumentException("path can't be null");
        try {
            InputStream is = getInputStreamFromUrl(path);
            if(is==null)return null;
            byte[] buffer = new byte[1024];
            int len = -1;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len=is.read(buffer))>0){
                bos.write(buffer,0,len);
            }
            return bos.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getBitmapResult(String path){
        try {
            InputStream is = getInputStreamFromUrl(path);
            if(is==null)return null;
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
