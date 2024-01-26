package com.microquation.linkedme.demo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * <p>分享页面</p>
 *
 * <p>如果web端不能生成深度链接(没有自己的web服务器),需要客户端自己创建并拼接深度链接</p>
 *
 * <p>如果web端能创建深度链接(有自己的web服务器),则原有的分享逻辑不变,分享页面无需修改</p>
 *
 * <p>Created by qipo on 15/7/29.</p>
 */

public class ShareActivity extends BaseActivity {

    public static final String TITLE = "title";
    public static final String PARAM_VIEW = "param_view";
    public static final String SHARE_CONTENT = "share_content";
    public static final String URL_PATH = "url_path";

    /**
     * 集成方原有分享的H5页面链接
     */
    private static final String H5_URL = "https://guide.lkme.cc/h5/";

    private String loadUrl;
    private String title;
    private String shareContent;
    private String url_path;

    private Toolbar toolbar;

    private WebView webView;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        title = getIntent().getStringExtra(TITLE);
        loadUrl = getIntent().getStringExtra(PARAM_VIEW);
        shareContent = getIntent().getStringExtra(SHARE_CONTENT);
        url_path = getIntent().getStringExtra(URL_PATH);
        findviews();
    }

    private void findviews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }

        webView = (WebView) findViewById(R.id.webView);
        loading = (ProgressBar) findViewById(R.id.loading);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(loadUrl);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                loading.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                loading.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

        });
    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.inflateMenu(R.menu.more);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //友盟社会化分享
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_more:
                        Toast.makeText(ShareActivity.this, "请自行处理分享逻辑", Toast.LENGTH_LONG).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 友盟分享回调类
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //attention to this below ,must add this
        Log.d("result", "onActivityResult");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}