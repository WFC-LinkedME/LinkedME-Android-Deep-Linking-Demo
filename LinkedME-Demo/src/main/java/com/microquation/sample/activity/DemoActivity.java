package com.microquation.sample.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.microquation.linkedme.android.callback.LMLinkCreateListener;
import com.microquation.linkedme.android.indexing.LMUniversalObject;
import com.microquation.linkedme.android.referral.LMError;
import com.microquation.linkedme.android.util.LinkProperties;
import com.microquation.sample.R;

/**
 * Created by qipo on 15/7/29.
 */

public class DemoActivity extends AppCompatActivity {

    private static final String TAG = DemoActivity.class.getName();
    private AppCompatButton demo_open_button;
    private AppCompatImageButton demo_link_button;
    private AppCompatEditText demo_edit;
    private AppCompatTextView demo_link_view;

    private Toolbar toolbar;

    public static final String BUTTON_ID = "0c7de66550848f947afd8bb5b14a2d38";
    public static final String CURRENT_LOCATION_LABEL = "当前位置";

    public static Intent newIntent(Context ctx) {
        return new Intent(ctx, DemoActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);
        findViews();
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        prepareButton();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        demo_open_button = (AppCompatButton) findViewById(R.id.demo_open_button);
        demo_link_button = (AppCompatImageButton) findViewById(R.id.demo_link_button);
        demo_edit = (AppCompatEditText) findViewById(R.id.demo_edit);
        demo_link_view = (AppCompatTextView) findViewById(R.id.demo_link_view);
        demo_open_button.setOnClickListener(v -> {
            String url = demo_edit.getEditableText().toString();
            if (!TextUtils.isEmpty(url)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    manager.setPrimaryClip(ClipData.newPlainText("Uri", url));
                    Toast.makeText(this, "已复制到剪贴板", Toast.LENGTH_SHORT).show();
                }
            }
        });
        demo_link_button.setOnClickListener(v -> {
            /**创建深度链接*/
            LinkProperties properties = new LinkProperties();
            properties.setChannel("weixin");
            properties.setFeature("share");
            properties.addTag("LinkedME");
            properties.addTag("Demo");
            properties.setStage("Test");
            properties.addControlParameter("$desktop_url", "http://www.linkedme.cc");
            properties.addControlParameter("$ios_url", "http://www.linkeme.cc");
            properties.setIOSKeyControlParameter("Demo");
            properties.setAndroidPathControlParameter("Demo");
            LMUniversalObject universalObject = new LMUniversalObject();
            universalObject.setTitle("Demo");
            universalObject.setContentDescription("LinkedME测试内容");
            universalObject.addContentMetadata("View", "Demo");
            universalObject.addContentMetadata("LinkedME", "Demo");

            // Async Link creation example
            universalObject.generateShortUrl(DemoActivity.this, properties, new LMLinkCreateListener() {
                @Override
                public void onLinkCreate(String url, LMError error) {
                    demo_edit.setText(url);
                    demo_link_view.setVisibility(View.VISIBLE);
                    demo_link_view.setText(universalObject.getMetadata().toString());
                    Log.i(TAG, "LinkedME onCreated " + url);
                }
            });

        });
        demo_edit.setOnClickListener(v -> {
        });
        demo_link_view.setOnClickListener(v -> {
        });
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    protected void prepareButton() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 12306);
            return;
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 12306) {
            prepareButton();
        }
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
