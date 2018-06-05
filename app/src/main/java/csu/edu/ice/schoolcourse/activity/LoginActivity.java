package csu.edu.ice.schoolcourse.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import csu.edu.ice.schoolcourse.R;
import csu.edu.ice.schoolcourse.bean.Student;
import csu.edu.ice.schoolcourse.utils.StudentDao;

public class LoginActivity extends AppCompatActivity {
    EditText etAccount;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etAccount = findViewById(R.id.etAccount);
        btnLogin = findViewById(R.id.btnLogin);
        //显示之前的用户名
        SharedPreferences sp = getSharedPreferences("config",MODE_PRIVATE);
        String username = sp.getString("username",null);
        if(username!=null) etAccount.setText(username);

        //登录按钮
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student(etAccount.getText().toString(),"password");
                StudentDao studentDao = new StudentDao(LoginActivity.this);
                studentDao.insertStudent(student);
                SharedPreferences.Editor editor = getSharedPreferences("config", MODE_PRIVATE).edit();
                editor.putString("username",student.getUsername()).commit();
            }
        });
        hook(btnLogin);

    }

    private void hook(View v) {
        try {
            Class clazzView = Class.forName("android.view.View");
            Method method = clazzView.getDeclaredMethod("getListenerInfo");
            method.setAccessible(true);
            Object listenerInfo = method.invoke(v);
            Class clazzInfo = Class.forName("android.view.View$ListenerInfo");
            Field field = clazzInfo.getDeclaredField("mOnClickListener");
            field.set(listenerInfo,new HookListener((View.OnClickListener) field.get(listenerInfo)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }


    private class HookListener implements View.OnClickListener{
        private View.OnClickListener originListener;

        public HookListener(View.OnClickListener originListener) {
            this.originListener = originListener;
        }

        @Override
        public void onClick(View v) {
            if(originListener!=null)originListener.onClick(v);
            Toast.makeText(LoginActivity.this, "我知道你点击了", Toast.LENGTH_SHORT).show();
        }
    }
}
