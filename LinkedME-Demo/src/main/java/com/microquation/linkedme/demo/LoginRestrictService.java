package com.microquation.linkedme.demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.microquation.linkedme.android.LinkedME;

public class LoginRestrictService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (LinkedMEDemoApp.getInstance().isShowedAd()) {
            if (SPHelper.getInstance(getApplicationContext()).getUserLogin()) {
                //已登录用户可以跳转到详情页面
                if (LinkedME.getInstance() != null) {
                    LinkedME.getInstance().setImmediate(true);
                }
            } else {
                //未登录用户不跳转到分享页面，而是跳转到登录页面，登录成功后跳转到详情页面
                //未登录用户不自动跳转
                if (LinkedME.getInstance() != null) {
                    LinkedME.getInstance().setImmediate(false);
                }
                Intent loginIntent = new Intent(this, LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
            }
        } else {
            if (LinkedME.getInstance() != null) {
                LinkedME.getInstance().setImmediate(false);
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
