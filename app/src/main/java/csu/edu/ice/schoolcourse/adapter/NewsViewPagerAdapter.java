package csu.edu.ice.schoolcourse.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import csu.edu.ice.schoolcourse.fragment.NewsItemFragment;

/**
 * Created by ice on 2018/5/18.
 */

public class NewsViewPagerAdapter extends FragmentPagerAdapter {

    String[] titles = {"体育","娱乐","军事","政治","体育","娱乐","军事","政治"};

    public NewsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        NewsItemFragment newsItemFragment = new NewsItemFragment();
        return newsItemFragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
