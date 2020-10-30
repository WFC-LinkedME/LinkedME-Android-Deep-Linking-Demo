package com.microquation.linkedme.demo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PrivacyActivity extends BaseActivity {

    private WebView webView;
    private String loadUrl = "https://docs.linkedme.cc/web/#/63";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        if (getIntent().getIntExtra("type", 0) == 1) {
            loadUrl = "https://docs.linkedme.cc/web/#/62";
        }
        webView.loadUrl(loadUrl);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });
    }
}
