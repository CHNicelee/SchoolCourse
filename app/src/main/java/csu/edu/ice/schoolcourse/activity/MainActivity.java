package csu.edu.ice.schoolcourse.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import csu.edu.ice.schoolcourse.R;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    private RecyclerView recyclerView;
    private TextView tvResult;
    private ImageButton btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        initView();
    }


    public void initView(){

        btnDelete = findViewById(R.id.ivDelete);
        tvResult = findViewById(R.id.tvResult);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(new DialAdapter("123456789*0#".toCharArray()));

        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivDelete:
                //删除按钮
                String result = tvResult.getText().toString();
                if(!TextUtils.isEmpty(result)){
                    result = result.substring(0,result.length()-1);
                    tvResult.setText(result);
                }
        }
    }

    private class DialAdapter extends RecyclerView.Adapter<MyViewHolder>{

        private final char[] numbers;
        private String letters = "";
        public DialAdapter(char[] numbers){
            this.numbers = numbers;
            for (int i = 0; i < 26; i++) {
                letters+=(char)('A'+i);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_dial, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return numbers.length;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.number.setText(numbers[position]+"");
            String s = null;
            if(position>0 && position <6){
                int start = (position-1)*3;
                int end = start+3;
                s = letters.substring(start,end);
            }else switch (position) {
                    case 6:
                        s = "PQRS";
                        break;
                    case 7:
                        s = "TUV";
                        break;
                    case 8:
                        s = "WXYZ";
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                }
            holder.letter.setText(s);

            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvResult.setText(tvResult.getText().toString()+numbers[position]);
                }
            });
        }

    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView number;
        public TextView letter;
        public View rootView;
        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            number = itemView.findViewById(R.id.number);
            letter = itemView.findViewById(R.id.letter);
        }
    }


}
