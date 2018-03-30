package com.example.prafulmishra.smartboard_alpha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        String url = "http://smartboard.cf/";
        WebView view = (WebView)this.findViewById(R.id.webview);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
    }
}
