package csu.edu.ice.schoolcourse.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import csu.edu.ice.schoolcourse.R;

/**
 * Created by ice on 2018/5/10.
 */

public class CoordinatorLayoutActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinatorlayout);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            list.add("第" +i+"个");
        }
        MyAdapter myAdapter = new MyAdapter(list);
        recyclerView.setAdapter(myAdapter);
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        private List<String> list;

        public MyAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(getLayoutInflater().inflate(R.layout.item_news,parent,false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textView.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView textView;
            public MyViewHolder(View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.tv_title);
            }
        }
    }

}
