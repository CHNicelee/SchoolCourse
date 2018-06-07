package csu.edu.ice.schoolcourse.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import csu.edu.ice.schoolcourse.R;

/**
 * Created by ice on 2018/5/29.
 */

public class VideoFragment extends Fragment {

    TextView textView;
    private String title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        textView = view.findViewById(R.id.tvContent);
        textView.setText(title);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            textView.setText(title);
        }
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        title = args.getString("text");
    }
}
