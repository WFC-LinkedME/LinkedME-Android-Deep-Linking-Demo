package com.microquation.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.microquation.sample.R;

/**
 * 启动页面
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }
}
