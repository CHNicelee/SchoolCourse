package csu.edu.ice.schoolcourse.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import csu.edu.ice.schoolcourse.R;
import csu.edu.ice.schoolcourse.fragment.NewsFragment;
import csu.edu.ice.schoolcourse.fragment.VideoFragment;

public class NewsActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup radioGroup;

    private NewsFragment newsFragment;
    private VideoFragment videoFragment;
    private FragmentManager fm;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        radioGroup = findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(this);

        fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        newsFragment = new NewsFragment();
        transaction.add(R.id.container,newsFragment).commit();

        fragmentList = new ArrayList<>();
        fragmentList.add(newsFragment);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction = fm.beginTransaction();

        hideFragments();

        switch (checkedId){

            case R.id.rb_news:
                //新闻
                if(newsFragment == null){
                    newsFragment = new NewsFragment();
                    transaction.add(R.id.container,newsFragment);
                }else {
                    transaction.show(newsFragment);
                }
                break;

            case R.id.rb_video:
                //视频
                if(videoFragment == null){
                    videoFragment = new VideoFragment();
                    transaction.add(R.id.container,videoFragment);
                    fragmentList.add(videoFragment);
                }else {
                    transaction.show(videoFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments() {
        FragmentTransaction transaction = fm.beginTransaction();
        for (Fragment fragment : fragmentList) {
            if(fragment!=null)transaction.hide(fragment);
        }
        transaction.commit();
    }
}

