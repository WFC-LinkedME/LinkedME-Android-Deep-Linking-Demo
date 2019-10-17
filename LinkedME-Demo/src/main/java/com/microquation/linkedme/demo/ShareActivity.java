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

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

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
    private static final String H5_URL = "https://www.linkedme.cc/h5/";

    private String loadUrl;
    private String title;
    private String shareContent;
    private String url_path;

    private Toolbar toolbar;

    private WebView webView;
    private ProgressBar loading;
    /**
     * 友盟分享的方法
     */
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(ShareActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ShareActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ShareActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ShareActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

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
//                        //web服务器无法创建深度链接时,客户端可选择创建
//                        //深度链接属性设置
//                        LinkProperties properties = new LinkProperties();
//                        //渠道
//                        properties.setChannel("");  //微信、微博、QQ
//                        //功能
//                        properties.setFeature("Share");
//                        //标签
//                        properties.addTag("LinkedME");
//                        properties.addTag("Demo");
//                        //阶段
//                        properties.setStage("Live");
//                        //设置该h5_url目的是为了iOS点击右上角lkme.cc时跳转的地址，一般设置为当前分享页面的地址
                        //H5_URL + url_path其实就是就是分享的链接，不要被这个所误导
//                        properties.setH5Url(H5_URL + url_path);
//                        //自定义参数,用于在深度链接跳转后获取该数据
//                        properties.addControlParameter("LinkedME", "Demo");
//                        properties.addControlParameter("View", loadUrl);
//                        LMUniversalObject universalObject = new LMUniversalObject();
//                        universalObject.setTitle(title);
//
//                        // 异步生成深度链接
//                        universalObject.generateShortUrl(ShareActivity.this, properties, new LMLinkCreateListener() {
//                            //https://www.lkme.cc/AfC/idFsW02l7
//                            @Override
//                            public void onLinkCreate(final String url, LMError error) {
                        final UMImage image = new UMImage(ShareActivity.this, "https://www.linkedme.cc/homepage2.jpg");
                        /**友盟分享化分享，分享的链接不单单是H5链接，而是携带深度链接的H5链接*/
                        new ShareAction(ShareActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA, SHARE_MEDIA.SMS, SHARE_MEDIA.EMAIL)
                                .setShareboardclickCallback(new ShareBoardlistener() {
                                    @Override
                                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

                                        //拼接深度链接,客户端将生成的深度链接值拼接到链接后,web端需要截取该url链接并放置到"打开APP"按钮下
//                                        UMWeb web = new UMWeb(H5_URL + url_path + "?linkedme=" + url);
                                        UMWeb web = new UMWeb(H5_URL + url_path);
                                        web.setTitle("LinkedME" + title);//标题
                                        web.setThumb(image);  //缩略图
                                        web.setDescription(shareContent);//描述
                                        //微信
                                        new ShareAction(ShareActivity.this)
                                                .setPlatform(share_media)
                                                .withMedia(web)
                                                .setCallback(umShareListener)
                                                .share();
                                    }
                                }).open();
//                            }
//                        });
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