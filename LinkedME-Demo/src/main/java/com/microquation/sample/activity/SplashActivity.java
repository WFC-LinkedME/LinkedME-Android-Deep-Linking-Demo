package com.microquation.sample.activity;

import android.content.Intent;
import android.os.Bundle;

import com.microquation.linkedme.android.LinkedME;
import com.microquation.linkedme.android.referral.PrefHelper;
import com.microquation.sample.R;

/**
 * 启动页面
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);


        PrefHelper.Debug(LinkedME.TAG, "origin onCreate: SplashActivity " + getIntent().getDataString());

        Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        PrefHelper.Debug(LinkedME.TAG, "origin onResume: SplashActivity " + getIntent().getDataString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        PrefHelper.Debug(LinkedME.TAG, "origin onStart: SplashActivity " + getIntent().getDataString());
    }
}
