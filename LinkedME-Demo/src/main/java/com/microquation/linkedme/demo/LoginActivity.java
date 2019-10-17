package com.microquation.linkedme.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * 用户登录页面，该页面针对跳转受用户登录限制的情况
 * Created by LinkedME06 on 16/11/23.
 */

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        final Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 此处设置广告已显示，防止用户登录后又展示广告页面，用户第一次登录不应展示广告
                LinkedMEDemoApp.getInstance().setShowedAd(true);
                SPHelper.getInstance(getApplicationContext()).setUserLogin(true);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
