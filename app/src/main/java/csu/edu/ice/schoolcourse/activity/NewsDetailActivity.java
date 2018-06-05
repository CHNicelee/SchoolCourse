package csu.edu.ice.schoolcourse.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import csu.edu.ice.schoolcourse.R;

public class NewsDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        toolbar = findViewById(R.id.toolbar);
        webView = findViewById(R.id.webView);

        initWebView();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initWebView() {
        String path = getIntent().getStringExtra("path");

        webView.loadUrl(path);
        webView.canGoBack();
        webView.canGoForward();
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

}
