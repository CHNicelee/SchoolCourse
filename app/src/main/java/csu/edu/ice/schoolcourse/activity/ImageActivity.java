package csu.edu.ice.schoolcourse.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import csu.edu.ice.schoolcourse.R;

public class ImageActivity extends AppCompatActivity {

    private static final int SET_BITMAP = 1;
    private static final String TAG = "ImageActivity";
    private static final int SET_TEXT = 2;
    EditText etUrl;
    ImageView ivPic;
    TextView tvContent;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: "+msg.what);
            if(msg.what == SET_BITMAP){
                ivPic.setImageBitmap((Bitmap) msg.obj);
            }else if(msg.what == SET_TEXT){
                tvContent.setText((String) msg.obj);
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        etUrl = findViewById(R.id.et_url);
        ivPic = findViewById(R.id.iv_pic);
        tvContent = findViewById(R.id.tv_content);
    }


    void getNetImage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png";
                    URL url = new URL(path);
                    URLConnection conn = url.openConnection();
                    conn.setConnectTimeout(3000);
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    Log.d(TAG, "run: "+is);
                    is.close();
                    handler.sendMessage(handler.obtainMessage(SET_BITMAP,bitmap));

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    void getText(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = "https://www.baidu.com/";
                    URL url = new URL(path);
                    URLConnection conn = url.openConnection();
                    conn.setConnectTimeout(3000);
                    conn.setRequestProperty("method","GET");
                    InputStream is = conn.getInputStream();

                    byte[] buffer = new byte[1024];
                    int len = -1;
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    while ((len=is.read(buffer))>0){
                        bos.write(buffer,0,len);
                    }
                    handler.sendMessage(handler.obtainMessage(SET_TEXT,bos.toString()));
                    bos.close();
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void onClick(View v){
        Log.d(TAG, "onClick: ");
        getText();

    }

}
