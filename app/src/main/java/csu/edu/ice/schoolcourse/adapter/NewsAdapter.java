package csu.edu.ice.schoolcourse.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import csu.edu.ice.schoolcourse.R;

/**
 * Created by ice on 2018/5/18.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder>{

    List<String> news;
    Context context;

    public NewsAdapter(List<String> news, Context context) {
        this.news = news;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_news, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvNews.setText(news.get(position));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvNews;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvNews = itemView.findViewById(R.id.tv_news);
        }
    }
}
