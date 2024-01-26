package com.microquation.linkedme.demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.microquation.linkedme.android.LinkedME;

/**
 * Created by LinkedME06 on 16/8/14.
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        //兼容14之前的版本需要在基类中添加以下代码
        super.onResume();
    }

    //该方法需要添加
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

}
