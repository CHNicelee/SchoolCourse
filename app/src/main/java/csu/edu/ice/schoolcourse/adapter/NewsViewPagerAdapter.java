package csu.edu.ice.schoolcourse.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import csu.edu.ice.schoolcourse.fragment.NewsItemFragment;

/**
 * Created by ice on 2018/5/18.
 */

public class NewsViewPagerAdapter extends FragmentPagerAdapter {

    String[] titles = {"头条","社会","国内","国际","娱乐","体育","军事","科技","财经","时尚"};
    String[] types = {"top","shehui","guonei","guoji","yule","tiyu","junshi","keji","caijing","shishang"};
    public NewsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        NewsItemFragment newsItemFragment = new NewsItemFragment();
        newsItemFragment.setArguments(types[position]);
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
