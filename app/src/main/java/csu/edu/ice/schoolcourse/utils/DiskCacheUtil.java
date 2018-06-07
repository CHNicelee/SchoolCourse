package csu.edu.ice.schoolcourse.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ice on 2018/6/6.
 */

public class DiskCacheUtil {

    private static DiskLruCache diskLruCache;

    public static File getDiskCacheDir(Context context, String uniqueName) {
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                ? context.getExternalCacheDir().getPath()
                : context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    public static DiskLruCache getDiskCache(Context context){
        if(diskLruCache != null)return diskLruCache;
        try {
            diskLruCache = DiskLruCache.open(getDiskCacheDir(context,"bitmap"),Utils.getAppVersion(context),1,10*1024*1024);
            return diskLruCache;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveBitmap(Context context,String key,Bitmap bitmap){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        DiskLruCache diskCache = DiskCacheUtil.getDiskCache(context);
        try {
            DiskLruCache.Editor editor = diskCache.edit(key);
            OutputStream os = editor.newOutputStream(0);
            os.write(baos.toByteArray());
            editor.commit();
            diskCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getBitmap(Context context,String key){
        DiskLruCache diskCache = DiskCacheUtil.getDiskCache(context);
        try {
            DiskLruCache.Snapshot snapShot = diskCache.get(key);
            if(snapShot==null)return null;
            InputStream is = snapShot.getInputStream(0);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
