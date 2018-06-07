package csu.edu.ice.schoolcourse.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import csu.edu.ice.schoolcourse.R;
import csu.edu.ice.schoolcourse.activity.NewsDetailActivity;
import csu.edu.ice.schoolcourse.adapter.ImageViewPagerAdapter;
import csu.edu.ice.schoolcourse.adapter.NewsAdapter;
import csu.edu.ice.schoolcourse.bean.News;
import csu.edu.ice.schoolcourse.network.NewsAsyncTask;
import csu.edu.ice.schoolcourse.utils.NewsDao;
import csu.edu.ice.schoolcourse.utils.Utils;

/**
 * Created by ice on 2018/5/18.
 */

public class NewsItemFragment extends Fragment {

    private final int INDEX_CHANGE = 1;
    private static final String TAG = "NewsItemFragment";
    ViewPager viewPager;
    RecyclerView recyclerView;
    LinearLayout doc_layout;
    private ProgressBar progressBar;

    private int pageIndex = 0;
    private ArrayList<View> pageViews;
    private String urlPath;
    private List<News> newsList;
    private List<News> topNews;
    public ImageViewPagerAdapter adapter;
    private boolean isShowing = true;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case INDEX_CHANGE:
                    if(isShowing) {
                        if(topNews!=null) {
                            pageIndex = (pageIndex + 1) % topNews.size();
                            viewPager.setCurrentItem(pageIndex, true);
                        }
                        startAutoPlay();
                    }
                    break;
            }
            return true;
        }
    });
    private String type;//英文 用于显示
    private String title;//中文



    public NewsItemFragment(){
        Log.d(TAG, "NewsItemFragment: "+this);
    }

    public void setArguments(String type, String title){
        Bundle bundle = new Bundle();
        bundle.putString("type",type);
        bundle.putString("title",title);
        setArguments(bundle);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        type = args.getString("type");
        title = args.getString("title");
        urlPath = "http://v.juhe.cn/toutiao/index?type="+type+"&key=c1178a0da6ea44c19d6b482cbbf6c326";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_item, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        recyclerView = view.findViewById(R.id.recyclerView);
        doc_layout = view.findViewById(R.id.doc_layout);
        progressBar = view.findViewById(R.id.progressBar);
        isShowing = true;

        if(Utils.isNetworkAvailable(getContext())){
            loadDataFromServer();
        }else if(!loadDataFromLocal()){
            //本地也没有
            Toast.makeText(getContext(), "请检查网络再重试", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private boolean loadDataFromLocal() {
        NewsDao newsDao = new NewsDao(getContext());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        newsList = newsDao.getNewsList(title,format.format(new Date(System.currentTimeMillis())).toString());
        if(newsList!=null && newsList.size()>0){
            initTopNews();
            Log.d(TAG, "loadDataFromLocal: 从本地读取");
            return true;
        }
        return false;
    }

    //初始化顶部的三个图片数据
    private void initTopNews() {

            topNews = new ArrayList<>();
            topNews.add(newsList.remove(0));
            topNews.add(newsList.remove(0));
            topNews.add(newsList.remove(0));

            progressBar.setVisibility(View.GONE);
            initViewPager();
            initRecyclerView();
    }

    @SuppressLint("StaticFieldLeak")
    private void loadDataFromServer() {
        new NewsAsyncTask(){
            @Override
            public void onPostExecute(List<News> news) {
                if(news ==null || news.size() == 0) {
                    if(!loadDataFromLocal()){
                        Toast.makeText(getContext(), "今日请求次数达到上限，或网络不佳，请重试", Toast.LENGTH_LONG).show();
                    }
                    return;
                }
                newsList = news;

                NewsDao newsDao = new NewsDao(getContext());

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                newsDao.deleteNewsByCategoryAndDate(title,format.format(new Date(System.currentTimeMillis())).toString());
                newsDao.insertNewsList(newsList);

                initTopNews();

                Log.d(TAG, "onPostExecute: 从网络读取");
            }
        }.execute(urlPath);
    }


    //初始化RecyclerView
    private void initRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new NewsAdapter(newsList,getActivity()));
    }

    /**
     * 初始化顶部的ViewPager
     */
    private void initViewPager() {

//        pageViews = new ArrayList<>();
//        pageViews.add(getViewItem(R.mipmap.jay,"最美的不是下雨天"));
//        pageViews.add(getViewItem(R.mipmap.jay2,"一路向北~"));
//        pageViews.add(getViewItem(R.mipmap.jay3,"为你翘课的那一天"));
        viewPager.setVisibility(View.VISIBLE);
        adapter = new ImageViewPagerAdapter(topNews, getActivity());
        viewPager.setAdapter(adapter);
//        viewPager.setPageMargin(20); //显示viewpager间距
//        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageIndex = position;
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


        viewPager.setOnTouchListener(new View.OnTouchListener() {
            boolean moved = false;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch: "+event.getAction());
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        moved = false;
                        isShowing= false;
                        handler.removeMessages(INDEX_CHANGE);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moved = true;
                        break;

                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        handler.removeMessages(INDEX_CHANGE);
                        isShowing= true;
                        startAutoPlay();
                        if(!moved) onPagerItemClicked();
                        break;
                }
                return false;
            }
        });

        initDocs();
//        startAutoPlay();

    }

    //当某一个滚动图片被点击了回调
    private void onPagerItemClicked() {
        int pos = viewPager.getCurrentItem();
        News news = topNews.get(pos);
        Intent intent = new Intent(getContext(), NewsDetailActivity.class);
        intent.putExtra("path",news.getUrl());
        getContext().startActivity(intent);
    }

    //自动滑动
    public void startAutoPlay() {
        isShowing = true;
        Message message = new Message();
        message.what = INDEX_CHANGE;
        handler.sendMessageDelayed(message,3000);
    }

    public void stopAutoPlay(){
        isShowing= false;
        handler.removeMessages(INDEX_CHANGE);
    }

    public void reset(){
        viewPager.setCurrentItem(0,false);
        pageIndex = 0;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isShowing= false;
        handler.removeMessages(INDEX_CHANGE);
    }

}
