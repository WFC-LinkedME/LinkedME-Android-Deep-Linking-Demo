package com.microquation.linkedme.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


/**
 * UriSchemeProcessActivity继承AppCompatActivity或者Activity，不继承基类
 */
public class UriSchemeProcessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 解决冷启动时显示纯白或纯黑背景
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        // 唤起自身
        Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        assert intent != null;
        intent.setFlags(getIntent().getFlags());
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO: 5/9/18 lipeng 无条件限制-start
        // App打开后无广告展示及登录等条件限制，直接在此处调用以下方法跳转到具体页面，若有条件限制请参考Demo
//        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
//            LinkedME.getInstance().setImmediate(true);
//        }
        // TODO: 5/9/18 lipeng 无条件限制-end

        // 防止唤起后台App后一直停留在该页面
        // ！！！ 请不要在 onCreate() 及 onStart() 方法中销毁 activity
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 请重写改方法并且设置该Activity的launchmode为singleTask
        setIntent(intent);
    }
}
