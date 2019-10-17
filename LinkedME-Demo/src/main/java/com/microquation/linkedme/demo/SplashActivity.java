package com.microquation.linkedme.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * 启动页面
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        Log.d("LinkedME", "origin onCreate: SplashActivity " + getIntent().getDataString());
        //处理首次安装点击打开切到后台,点击桌面图标再回来重启的问题及通过应用宝唤起在特定条件下重走逻辑的问题
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            // Activity was brought to front and not created,
            // Thus finishing this will get us to the last viewed activity
            finish();
            return;
        }
        Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
        startActivity(intent);

        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LinkedME", "origin onResume: SplashActivity " + getIntent().getDataString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LinkedME", "origin onStart: SplashActivity " + getIntent().getDataString());
    }
}
