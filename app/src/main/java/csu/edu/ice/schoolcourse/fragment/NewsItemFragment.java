package csu.edu.ice.schoolcourse.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import csu.edu.ice.schoolcourse.R;
import csu.edu.ice.schoolcourse.adapter.ImageViewPagerAdapter;
import csu.edu.ice.schoolcourse.adapter.NewsAdapter;

/**
 * Created by ice on 2018/5/18.
 */

public class NewsItemFragment extends Fragment {

    ViewPager viewPager;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_item, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        recyclerView = view.findViewById(R.id.recyclerView);

        initViewPager();
        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        String[] data = {"今天好开心","明天要考试","考完去吃饭","今天好开心","明天要考试","考完去吃饭","今天好开心","明天要考试","考完去吃饭"};
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new NewsAdapter(Arrays.asList(data),getContext()));
    }

    private void initViewPager() {

        List<View> views = new ArrayList<>();
        views.add(getViewItem(R.mipmap.jay,"最美的不是下雨天"));
        views.add(getViewItem(R.mipmap.jay2,"一路向北~"));
        views.add(getViewItem(R.mipmap.jay3,"为你翘课的那一天"));

        viewPager.setAdapter(new ImageViewPagerAdapter(views));
        viewPager.setPageMargin(20); //显示viewpager间距
        viewPager.setOffscreenPageLimit(3);
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
