package csu.edu.ice.schoolcourse.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.TabPageIndicator;

import csu.edu.ice.schoolcourse.R;
import csu.edu.ice.schoolcourse.adapter.NewsViewPagerAdapter;

/**
 * Created by ice on 2018/5/18.
 */

public class NewsFragment extends Fragment {

    private ViewPager viewPager;
    private TabPageIndicator tabStrip;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        tabStrip = view.findViewById(R.id.tabStrip);

        final NewsViewPagerAdapter adapter = new NewsViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabStrip.setViewPager(viewPager);


        return view;
    }

}
