package csu.edu.ice.schoolcourse;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import csu.edu.ice.schoolcourse.fragment.NewsFragment;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        NewsFragment newsFragment = new NewsFragment();
        transaction.add(R.id.container,newsFragment);
        transaction.commit();
    }

}

