package csu.edu.ice.schoolcourse;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class QQActivity extends AppCompatActivity {
    private TextView tvChoice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);
        tvChoice = findViewById(R.id.tv_choice);
    }

    private int chooseIndex;
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_choose:
                final String[] singleItems = new String[]{"在线", "离线"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("状态选择")
                        .setSingleChoiceItems(singleItems, chooseIndex, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                chooseIndex = which;
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tvChoice.setText(singleItems[chooseIndex]);
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
                break;

            case R.id.btn_mulChoose:
                final String[] fruits = {"苹果","香蕉","芒果"};
                new AlertDialog.Builder(this)
                        .setMultiChoiceItems(fruits, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                            }
                        })
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消",null)
                        .show();
                break;
        }
    }
}
