package com.microquation.sample.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class AppsActivity extends AppCompatActivity {
    //分享的H5页面
    private static final String LIVE_H5_URL = "https://www.linkedme.cc/h5/partner?linkedme=";

    //应用方
    private AppCompatImageButton id_kr;
    private AppCompatImageButton id_nuggets;
    private AppCompatImageButton id_pocket;
    private AppCompatImageButton id_table;
    private AppCompatImageButton id_have;
    private AppCompatImageButton id_day;
    private AppCompatImageButton id_beijing;
    private AppCompatImageButton id_cattle;

    private Toolbar toolbar;
    public static Intent newIntent(Context ctx) {
        Intent intent=new Intent(ctx,AppsActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVIT);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apps_activity);
        findviews();
    }

    private void findviews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        id_kr = (AppCompatImageButton) findViewById(R.id.id_kr);
        id_nuggets = (AppCompatImageButton) findViewById(R.id.id_nuggets);
        id_pocket = (AppCompatImageButton) findViewById(R.id.id_pocket);
        id_table = (AppCompatImageButton) findViewById(R.id.id_table);
        id_have = (AppCompatImageButton) findViewById(R.id.id_have);
        id_day = (AppCompatImageButton) findViewById(R.id.id_day);
        id_beijing = (AppCompatImageButton) findViewById(R.id.id_beijing);
        id_cattle = (AppCompatImageButton) findViewById(R.id.id_cattle);
        id_kr.setOnClickListener(v -> execStartExternalApp("com.android36kr.app"));
        id_nuggets.setOnClickListener(v -> execStartExternalApp("com.daimajia.gold"));
        id_pocket.setOnClickListener(v -> execStartExternalApp("com.pitaya.daokoudai"));
        id_table.setOnClickListener(v -> {});
        id_have.setOnClickListener(v -> {});
        id_day.setOnClickListener(v -> {});
        id_beijing.setOnClickListener(v -> {});
        id_cattle.setOnClickListener(v -> execStartExternalApp("com.geekniu"));
    }

    protected void execStartExternalApp(String pkgName) {
        Intent intent = getPackageManager().getLaunchIntentForPackage(pkgName);
        if (intent != null && intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + pkgName));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.inflateMenu(R.menu.more);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_more:
                    /**创建深度链接*/
                    LinkProperties properties = new LinkProperties();
                    properties.setChannel("Wechat");
                    properties.setFeature("sharing");
                    properties.addTag("LinkedME");
                    properties.addTag("test");
                    properties.setStage("Test");
                    properties.addControlParameter("$desktop_url", "http://www.linkedme.cc");
                    properties.addControlParameter("$ios_url", "http://www.linkeme.cc");
                    properties.setIOSKeyControlParameter("Partners");
                    properties.setAndroidPathControlParameter("Partners");
                    LMUniversalObject universalObject = new LMUniversalObject();
                    universalObject.setTitle("Partners");
                    universalObject.setContentDescription("LinkedME测试内容");
                    universalObject.addContentMetadata("View", "Partners");
                    universalObject.addContentMetadata("LinkedME", "Demo");

                    // Async Link creation example
                    universalObject.generateShortUrl(AppsActivity.this, properties, new LMLinkCreateListener() {
                        @Override
                        public void onLinkCreate(String url, LMError error) {
                            UMImage image = new UMImage(AppsActivity.this, "http://api.linkedme.cc/homepage2.jpg");
                            /**友盟分享化分享，分享的链接不单单是H5链接，而是携带深度链接的H5链接*/
                            new ShareAction(AppsActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA)
                                    .withText("LinkedME产品已经被众多移动应用垂青, 比如Uber、滴滴、36Kr、小饭桌、每天、道口贷等等, 更多应用正在集成中...;")
                                    .withTitle("LinkedME应用方")
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
                Toast.makeText(AppsActivity.this,platform + " 收藏成功啦",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(AppsActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(AppsActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(AppsActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
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