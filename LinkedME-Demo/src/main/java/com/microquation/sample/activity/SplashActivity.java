package com.microquation.sample.activity;

import android.content.Intent;
import android.os.Bundle;

import com.microquation.sample.R;

/**
 * 启动页面
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
