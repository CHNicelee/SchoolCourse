package csu.edu.ice.schoolcourse.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;

import csu.edu.ice.schoolcourse.R;
import csu.edu.ice.schoolcourse.utils.Utils;

public class NewsDetailActivity extends AppCompatActivity {
    private static final String TAG = NewsDetailActivity.class.getName();
    Toolbar toolbar;
    WebView webView;
    ProgressBar progressBar;
    EditText editText;
    int screenWidth;
    View root;
    double leftRate = 0.2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        root = getWindow().getDecorView();
        screenWidth = Utils.getScreenWidth(this);

        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        editText = findViewById(R.id.et_comment);
        initWebView();
        webView.requestFocus();
//        initToolbar();

    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initWebView() {
        String path = getIntent().getStringExtra("path");
        Log.d(TAG, "initWebView: "+path);
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(false);

        webView.loadUrl(path);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    public void onClick(View view) {
        finish();
    }

    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            Log.d(TAG, "onProgressChanged: "+newProgress);
            if(newProgress == 100){
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        }

    }

    boolean isTouchEdge;
    float firstX, firstY;
    float lastX,lastY;
    boolean isFirstMove;
    float[] xPos = new float[3];
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float x = ev.getX(),y = ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                firstX = x;
                firstY = y;
                isFirstMove = true;
                if(ev.getX()<screenWidth*leftRate){
                    //在屏幕侧边滑动
                    isTouchEdge = true;
                }else{
                    isTouchEdge = false;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if(!isTouchEdge) return super.dispatchTouchEvent(ev);

                for (int i = xPos.length-1;i>0; i--) {
                    xPos[i] = xPos[i-1];
                }
                xPos[0] = x;

                if(isFirstMove){
                    isFirstMove = false;
                    if(Math.abs(y- firstY)>Math.abs(x- firstX)){
                        //在y轴距离滑的比较远
                        isTouchEdge = false;
                        return super.dispatchTouchEvent(ev);
                    }
                }

                float dx = x - firstX;
                if(dx>=0){
                    root.setX(dx);//滑动
                    root.invalidate();
                }
                lastX = x;
                break;

            case MotionEvent.ACTION_UP:
                int totalX = 0;
                for (int i = 0; i < xPos.length-1; i++) {
                    if(xPos[i]>xPos[i+1]){
                        totalX+=1;
                    }else{
                        totalX-=1;
                    }
                }

                if(isTouchEdge){
                    ValueAnimator valueAnimator = null;

                    if(totalX>0){
                         valueAnimator = ValueAnimator.ofInt((int)x,screenWidth);
                    }else{
                        valueAnimator = ValueAnimator.ofInt((int)lastX,0);
                    }
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            root.setX((Integer) animation.getAnimatedValue());
                            if(animation.getAnimatedValue().equals(screenWidth)){
                                finish();
                            }
                        }
                    });
                    valueAnimator.setDuration(300);
                    valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    valueAnimator.start();

                }
                break;
        }


        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(webView!=null)
            webView.stopLoading();
    }
}
