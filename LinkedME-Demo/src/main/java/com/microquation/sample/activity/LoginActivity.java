package com.microquation.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.microquation.linkedme.android.LinkedME;
import com.microquation.sample.R;

/**
 * 用户登录页面，该页面针对跳转受用户登录限制的情况
 * Created by LinkedME06 on 16/11/23.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPHelper.getInstance(getApplicationContext()).setUserLogin(true);
                //登录成功后，跳转到分享页面
                LinkedME.getInstance().setImmediate(true);
                finish();
            }
        });
    }
}
