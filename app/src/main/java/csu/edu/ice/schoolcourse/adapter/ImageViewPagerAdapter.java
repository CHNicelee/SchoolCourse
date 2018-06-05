package csu.edu.ice.schoolcourse.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import csu.edu.ice.schoolcourse.R;
import csu.edu.ice.schoolcourse.bean.News;
import csu.edu.ice.schoolcourse.network.ImageAsyncTask;
import csu.edu.ice.schoolcourse.utils.ImageCache;

/**
 * Created by ice on 2018/5/18.
 */

public class ImageViewPagerAdapter extends PagerAdapter {

    private final Context context;
    List<News> newsList;
    View[] views;
    ImageCache imageCache;
    public ImageViewPagerAdapter(List<News> newsList,Context context) {
        this.newsList = newsList;
        this.context = context;
        views = new View[newsList.size()];

        int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);// kB
        int cacheSize = maxMemory / 8;
        imageCache = new ImageCache(cacheSize);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if(views[position]==null){
            final News news = newsList.get(position);
            View v = View.inflate(context, R.layout.item_top_image,null);
            final ImageView imageView = v.findViewById(R.id.imageView);
            TextView textView = v.findViewById(R.id.title);
            textView.setText(news.getTitle());

            final Bitmap cacheBitmap = imageCache.get(news.getThumbnail_pic_s());
            if(cacheBitmap!=null){
                imageView.setImageBitmap(cacheBitmap);
            }else{
                new ImageAsyncTask(){
                    @Override
                    public void onPostExecute(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                        imageCache.put(news.getThumbnail_pic_s(),bitmap);
                    }
                }.execute(news.getThumbnail_pic_s());
            }

            views[position] = v;
        }
        container.addView(views[position]);
        return views[position];
    }

    @Override
    public int getCount() {
        return views.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views[position]);
    }

    @Override
    public float getPageWidth(int position) {
        return 1;
    }
}
