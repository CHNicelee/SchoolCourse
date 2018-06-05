package csu.edu.ice.schoolcourse.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by ice on 2018/6/3.
 */

public class ImageCache extends LruCache<String,Bitmap>{

    public ImageCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() /1024; //kb
    }
}
