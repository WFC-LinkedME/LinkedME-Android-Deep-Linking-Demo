package com.microquation.sample.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.microquation.linkedme.android.callback.LMLinkCreateListener;
import com.microquation.linkedme.android.indexing.LMUniversalObject;
import com.microquation.linkedme.android.referral.LMError;
import com.microquation.linkedme.android.util.LinkProperties;
import com.microquation.sample.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;

/**
 * Created by qipo on 15/7/29.
 */

public class SummaryActivity extends AppCompatActivity {
    private static final String LIVE_H5_URL = "https://www.linkedme.cc/h5/summary?linkedme=";
    private Toolbar toolbar;
    private WebView webView;

    public static Intent newIntent(Context ctx) {
        return new Intent(ctx, SummaryActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction_activity);
        findViews();
    }

    private void findViews() {
        webView = (WebView) findViewById(R.id.webView);
        toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        }
        webView.loadUrl("file:///android_asset/summary.html");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.inflateMenu(R.menu.more);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_more:
                    /**创建深度链接*/
                    LinkProperties properties = new LinkProperties();
                    properties.setChannel("");  //微信、微博、QQ
                    properties.setFeature("Share");
                    properties.addTag("LinkedME");
                    properties.addTag("Summary");
                    properties.setStage("Live");
                    properties.addControlParameter("LinkedME", "Demo");
                    properties.addControlParameter("View", "Summary");
                    LMUniversalObject universalObject = new LMUniversalObject();
                    universalObject.setTitle("Summary");

                    // Async Link creation example
                    universalObject.generateShortUrl(SummaryActivity.this, properties, new LMLinkCreateListener() {
                        @Override
                        public void onLinkCreate(String url, LMError error) {
                            UMImage image = new UMImage(SummaryActivity.this, "http://api.linkedme.cc/homepage2.jpg");
                            /**友盟分享化分享，分享的链接不单单是H5链接，而是携带深度链接的H5链接*/
                            new ShareAction(SummaryActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA)
                                    .withText("LinkedME是移动应用跨平台链接的解决方案,针对应用内所有页面定义唯一链接,使得移动应用不再是信息孤岛...")
                                    .withTitle("LinkedME摘要")
                                    .withMedia(image)
                                    .withTargetUrl(LIVE_H5_URL + url)
                                    .setCallback(umShareListener)
                                    .open();
                        }
                    });
                    return true;
                default:
                    return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 友盟分享的方法
     */

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            if(platform.name().equals("WEIXIN_FAVORITE")){
                Toast.makeText(SummaryActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(SummaryActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(SummaryActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(SummaryActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 友盟分享回调类
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
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

