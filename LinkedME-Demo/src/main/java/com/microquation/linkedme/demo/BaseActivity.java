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
        // TODO: 27/02/2017 广告演示：退到后台再回到前台后展示广告
        if (LinkedMEDemoApp.getInstance().isInBackground() &&
                !LinkedMEDemoApp.getInstance().isShowedAd()) {
            // 只有从后台拉起到前台并且用户登录的情况下才会显示广告
            Log.i("LinkedME", "onResume: 开始显示广告");
            //延迟跳转是为了防止页面未完成就跳转到广告页面了
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //一些状态的修改
                    LinkedMEDemoApp.getInstance().setInBackground(false);
                    LinkedMEDemoApp.getInstance().setShowedAd(true);
                    Intent intent = new Intent(BaseActivity.this, AdvertisementActivity.class);
                    startActivity(intent);
                }
            }, 10);
        }
        super.onResume();
    }

    //该方法需要添加
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

}
