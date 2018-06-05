package csu.edu.ice.schoolcourse.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import csu.edu.ice.schoolcourse.R;
import csu.edu.ice.schoolcourse.activity.NewsDetailActivity;
import csu.edu.ice.schoolcourse.bean.News;
import csu.edu.ice.schoolcourse.network.ImageAsyncTask;
import csu.edu.ice.schoolcourse.utils.ImageCache;

/**
 * Created by ice on 2018/5/18.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder>{

    private static final String TAG = "NewsAdapter";
    private final List<News> newsList;
    Activity context;
    private ImageCache imageCache;

    public NewsAdapter(List<News> newsList, Activity context) {
        this.newsList = newsList;
        this.context = context;

        int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);// kB
        int cacheSize = maxMemory / 8;
        imageCache = new ImageCache(cacheSize);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = context.getLayoutInflater().inflate(R.layout.item_news, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final News news = newsList.get(position);
        holder.tvTitle.setText(news.getTitle());
        holder.tvDate.setText(news.getDate().substring(10));
        holder.tvAuthorName.setText(news.getAuthorName());
        holder.ivThumbnail.setTag(news.getThumbnail_pic_s());
        holder.ivThumbnail.setImageBitmap(null);
        Log.d(TAG, "onBindViewHolder: "+news.getThumbnail_pic_s());

        final Bitmap cacheBitmap = imageCache.get(news.getThumbnail_pic_s());
        if(cacheBitmap!=null){
            holder.ivThumbnail.setImageBitmap(cacheBitmap);
        }else{
            new ImageAsyncTask(){
                @Override
                public void onPostExecute(Bitmap bitmap) {
                    if(bitmap == null)return;
                    if(holder.ivThumbnail.getTag().equals(news.getThumbnail_pic_s())) {
                        holder.ivThumbnail.setImageBitmap(bitmap);
                        imageCache.put(news.getThumbnail_pic_s(),bitmap);
                    }
                }
            }.execute(news.getThumbnail_pic_s());
        }

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("path",news.getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTitle,tvAuthorName,tvDate;
        public ImageView ivThumbnail;
        public View root;
        public MyViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthorName = itemView.findViewById(R.id.tv_author_name);
            tvDate = itemView.findViewById(R.id.tv_date);
            ivThumbnail = itemView.findViewById(R.id.iv_thumbnail);
        }
    }
}
