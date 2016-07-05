package com.microquation.sample.activity;

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
 *
 * <p>测试页面分享及打开应用市场下载指定APP</p>
 *
 * <p>Created by qipo on 15/7/29.</p>
 *
 */

public class AppsActivity extends AppCompatActivity {
    /**
     * 集成方原有分享的H5页面链接
     */
    private static final String H5_URL = "https://www.linkedme.cc/h5/partner";
    /**
     * 集成方根据原有的H5页面链接创建深度链接,即在链接后拼接一个linkedme参数,随后在分享时将生成的深度链接参数值拼接到该链接后面
     */
    private static final String LIVE_H5_URL = H5_URL + "?linkedme=";

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
    /**
     * 友盟分享的方法
     */
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(AppsActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AppsActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(AppsActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(AppsActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

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
        id_kr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execStartExternalApp("com.android36kr.app");
            }
        });
        id_nuggets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execStartExternalApp("com.daimajia.gold");
            }
        });
        id_pocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execStartExternalApp("com.pitaya.daokoudai");
            }
        });
        id_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execStartExternalApp("cn.xfz.app");
            }
        });
        id_have.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Android无此应用
            }
        });
        id_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Android无此应用
            }
        });
        id_beijing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Android无此应用
            }
        });
        id_cattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execStartExternalApp("com.geekniu");
            }
        });
    }

    /**
     * 未安装则跳转到应用市场指定的APP页面或已安装则打开本地应用
     *
     * @param pkgName APP的包名
     */
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
        //友盟社会化分享
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_more:
                        /**创建深度链接*/
                        //深度链接属性设置
                        LinkProperties properties = new LinkProperties();
                        //渠道
                        properties.setChannel("");  //微信、微博、QQ
                        //功能
                        properties.setFeature("Share");
                        //标签
                        properties.addTag("LinkedME");
                        properties.addTag("Partner");
                        //阶段
                        properties.setStage("Live");
                        //自定义参数,用于在深度链接跳转后获取该数据
                        properties.addControlParameter("LinkedME", "Demo");
                        properties.addControlParameter("View", "Partner");
                        LMUniversalObject universalObject = new LMUniversalObject();
                        universalObject.setTitle("Partner");

                        // 异步生成深度链接
                        universalObject.generateShortUrl(AppsActivity.this, properties, new LMLinkCreateListener() {
                            //https://www.lkme.cc/AfC/idFsW02l7
                            @Override
                            public void onLinkCreate(String url, LMError error) {
                                UMImage image = new UMImage(AppsActivity.this, "http://api.linkedme.cc/homepage2.jpg");
                                /**友盟分享化分享，分享的链接不单单是H5链接，而是携带深度链接的H5链接*/
                                new ShareAction(AppsActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA)
                                        .withText("LinkedME产品已经被众多移动应用垂青, 比如Uber、滴滴、36Kr、小饭桌、每天、道口贷等等, 更多应用正在集成中...;")
                                        .withTitle("LinkedME应用方")
                                        .withMedia(image)
                                        // 拼接深度链接,将生成的深度链接值拼接到链接后
                                        .withTargetUrl(LIVE_H5_URL + url)
                                        .setCallback(umShareListener)
                                        .open();
                            }
                        });
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
        //友盟社会化分享
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