package com.microquation.sample.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.microquation.linkedme.android.callback.LMLinkCreateListener;
import com.microquation.linkedme.android.indexing.LMUniversalObject;
import com.microquation.linkedme.android.referral.LMError;
import com.microquation.linkedme.android.util.LinkProperties;
import com.microquation.sample.R;

/**
 * <p>生成深度链接Demo</p>
 * <p>
 * <p>Created by qipo on 15/7/29.</p>
 */

public class DemoActivity extends AppCompatActivity {

    private static final String TAG = DemoActivity.class.getName();
    /**
     * 复制按钮
     */
    private AppCompatButton demo_open_button;
    /**
     * 生成深度链接按钮
     */
    private AppCompatImageButton demo_link_button;
    /**
     * 生成的深度链接
     */
    private AppCompatEditText demo_edit;
    /**
     * 深度链接自定义参数展示
     */
    private AppCompatTextView demo_link_view;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);
        findViews();
        if (null != getIntent() && !TextUtils.isEmpty(getIntent().getStringExtra("keyValue"))) {
            demo_link_view.setText(getIntent().getStringExtra("keyValue"));
        }
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        demo_open_button = (AppCompatButton) findViewById(R.id.demo_open_button);
        demo_link_button = (AppCompatImageButton) findViewById(R.id.demo_link_button);
        demo_edit = (AppCompatEditText) findViewById(R.id.demo_edit);
        demo_link_view = (AppCompatTextView) findViewById(R.id.demo_link_view);

        demo_open_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = demo_edit.getEditableText().toString();
                if (!TextUtils.isEmpty(url)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        manager.setPrimaryClip(ClipData.newPlainText("Uri", url));
                    } else {
                        //Android 10 及以下版本复制到剪切板
                        android.text.ClipboardManager manager = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        manager.setText(url);
                    }
                    Toast.makeText(DemoActivity.this, "已复制到剪贴板", Toast.LENGTH_SHORT).show();
                }
            }
        });
        demo_link_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**创建深度链接*/
                //深度链接属性设置
                final LinkProperties properties = new LinkProperties();
                //渠道
                properties.setChannel("");  //微信、微博、QQ
                //功能
                properties.setFeature("Share");
                //标签
                properties.addTag("LinkedME");
                properties.addTag("Demo");
                //阶段
                properties.setStage("Live");
                //设置该h5_url目的是为了iOS点击右上角lkme.cc时跳转的地址，一般设置为当前分享页面的地址
                properties.setH5Url("https://linkedme.cc/h5/feature");
                //自定义参数,用于在深度链接跳转后获取该数据
                properties.addControlParameter("LinkedME", "Demo");
                properties.addControlParameter("View", "Demo");
                LMUniversalObject universalObject = new LMUniversalObject();
                universalObject.setTitle("Demo");

                // Async Link creation example
                universalObject.generateShortUrl(DemoActivity.this, properties, new LMLinkCreateListener() {
                    @Override
                    public void onLinkCreate(String url, LMError error) {
                        if (error == null) {
                            //url为生成的深度链接
                            demo_edit.setText(url);
                            //获取深度链接对应的自定义参数数据
                            demo_link_view.setText(properties.getControlParams().toString());
                            Log.i(TAG, "LinkedME onCreated " + url);
                        } else {
                            Log.i(TAG, "创建深度链接失败！失败原因：" + error.getMessage());
                        }
                    }
                });
            }
        });
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
