package com.yefeihu.pastryprogressbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by YeFeiHu on 2016/6/2.
 */
public class TestActivity extends AppCompatActivity {
    private PastryProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        WebView vw = (WebView) findViewById(R.id.wv);
        progressBar = (PastryProgressBar) findViewById(R.id.progress);
        progressBar.setMaxPregress(100);

        vw.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setNowProgress(newProgress);
            }

        });

        vw.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }
        });
        vw.loadUrl("http://m.duba.com");
    }
}
