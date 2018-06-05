package csu.edu.ice.schoolcourse.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import csu.edu.ice.schoolcourse.R;
import csu.edu.ice.schoolcourse.adapter.ImageViewPagerAdapter;
import csu.edu.ice.schoolcourse.adapter.NewsAdapter;
import csu.edu.ice.schoolcourse.bean.News;
import csu.edu.ice.schoolcourse.network.NewsAsyncTask;

/**
 * Created by ice on 2018/5/18.
 */

public class NewsItemFragment extends Fragment {

    private final int INDEX_CHANGE = 1;
    private static final String TAG = "NewsItemFragment";
    ViewPager viewPager;
    RecyclerView recyclerView;
    LinearLayout doc_layout;
    private int pageIndex = 0;
    private ArrayList<View> pageViews;
    private String urlPath;
    private List<News> newsList;
    private List<News> topNews;
    public NewsItemFragment(){
        Log.d(TAG, "NewsItemFragment: "+this);
    }

    public void setArguments(String type){
        Bundle bundle = new Bundle();
        bundle.putString("type",type);
        setArguments(bundle);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        String type = args.getString("type");
        urlPath = "http://v.juhe.cn/toutiao/index?type="+type+"&key=c1178a0da6ea44c19d6b482cbbf6c326";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_item, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        recyclerView = view.findViewById(R.id.recyclerView);
        doc_layout = view.findViewById(R.id.doc_layout);
            new NewsAsyncTask(){
                @Override
                public void onPostExecute(List<News> news) {
                    newsList = news;
                    topNews = new ArrayList<>();
                    topNews.add(newsList.remove(0));
                    topNews.add(newsList.remove(0));
                    topNews.add(newsList.remove(0));
                    initViewPager();
                    initRecyclerView();
                }
            }.execute(urlPath);
        isShowing = true;
        return view;
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new NewsAdapter(newsList,getActivity()));
    }


    private boolean isShowing = true;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case INDEX_CHANGE:
                    if(isShowing) {
                        pageIndex = (pageIndex + 1) % topNews.size();
                        viewPager.setCurrentItem(pageIndex, true);
                        startAutoPlay();
                    }
                    break;
            }
            return true;
        }
    });


    /**
     * 初始化顶部的ViewPager
     */
    private void initViewPager() {

//        pageViews = new ArrayList<>();
//        pageViews.add(getViewItem(R.mipmap.jay,"最美的不是下雨天"));
//        pageViews.add(getViewItem(R.mipmap.jay2,"一路向北~"));
//        pageViews.add(getViewItem(R.mipmap.jay3,"为你翘课的那一天"));

        viewPager.setAdapter(new ImageViewPagerAdapter(topNews,getActivity()));
//        viewPager.setPageMargin(20); //显示viewpager间距
//        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < doc_layout.getChildCount(); i++) {
                    View view = doc_layout.getChildAt(i);
                    if(i == position) {
                        view.setEnabled(false);
                    }else{
                        view.setEnabled(true);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initDocs();
        startAutoPlay();
    }

    //自动滑动
    private void startAutoPlay() {
        if(!isShowing){
            return;
        }
        Message message = new Message();
        message.what = INDEX_CHANGE;
        handler.sendMessageDelayed(message,3000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isShowing= false;
        handler.removeMessages(INDEX_CHANGE);
    }

    //初始化ViewPager的圆点指示器
    private void initDocs() {
        for (int i = 0; i < topNews.size(); i++) {
            View view = new View(getActivity());
            doc_layout.addView(view);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.height = 20;
            params.width = 20;
            params.leftMargin = 5;
            params.rightMargin = 5;
            view.setLayoutParams(params);
            view.setEnabled(true);
            if(i==0)view.setEnabled(false);
            view.setBackgroundResource(R.drawable.select_doc);
        }
    }

    private View getViewItem(int resId,String text){
        View view = getLayoutInflater().inflate(R.layout.item_top_image, null);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(resId);
        TextView textView = view.findViewById(R.id.title);
        textView.setText(text);
        return view;
    }



}
