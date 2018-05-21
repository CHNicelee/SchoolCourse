package csu.edu.ice.schoolcourse.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import csu.edu.ice.schoolcourse.R;
import csu.edu.ice.schoolcourse.adapter.NewsViewPagerAdapter;

/**
 * Created by ice on 2018/5/18.
 */

public class NewsFragment extends Fragment {

    private ViewPager viewPager;
    private PagerSlidingTabStrip tabStrip;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        tabStrip = view.findViewById(R.id.tabStrip);

        viewPager.setAdapter(new NewsViewPagerAdapter(getActivity().getSupportFragmentManager()));
        tabStrip.setViewPager(viewPager);
        return view;
    }




}
