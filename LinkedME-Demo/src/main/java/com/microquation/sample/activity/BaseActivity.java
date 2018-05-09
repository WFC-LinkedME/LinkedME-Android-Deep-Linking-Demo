package com.microquation.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.microquation.linkedme.android.LinkedME;

/**
 * Created by LinkedME06 on 16/8/14.
 *
 * SDK 1.0.6及以后采用其他更优方案，基类集成方案可忽略！！！
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //兼容14之前的版本需要在基类中添加以下代码
        LinkedME.getInstance().onLMCreated(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        //兼容14之前的版本需要在基类中添加以下代码
        LinkedME.getInstance().onLMStarted(this);
        // TODO: 27/02/2017 广告演示：退到后台再回到前台后展示广告
        if ((LinkedMEDemoApp.getInstance().isInBackground() && !LinkedMEDemoApp.getInstance().isShowedAd()) ||
                !SPHelper.getInstance(getApplicationContext()).getUserLogin()) {
            // 这里模拟了开发者集成可能需要处理的两个逻辑：
            // 1. 当应用被从后台拉起并且需要展示过广告时展示广告；
            // 2. 用户打开App未登录时需要跳转到登录页面进行登录；

            //在此处添加跳转限制，目的是为了在广告展示完毕后或者登录后再进行跳转，
            //需要在super.onStart()方法调用之前添加该限制，否则无法生效!!!
            LinkedME.getInstance().addJumpConstraint();
        }
        super.onStart();

    }

    @Override
    protected void onResume() {
        //兼容14之前的版本需要在基类中添加以下代码
        LinkedME.getInstance().onLMResumed(this);
        // TODO: 27/02/2017 广告演示：退到后台再回到前台后展示广告
        if (LinkedMEDemoApp.getInstance().isInBackground() &&
                !LinkedMEDemoApp.getInstance().isShowedAd() &&
                SPHelper.getInstance(getApplicationContext()).getUserLogin()) {
            // 只有从后台拉起到前台并且用户登录的情况下才会显示广告
            Log.i(LinkedME.TAG, "onResume: 开始显示广告");
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
    @Override
    protected void onPause() {
        //兼容14之前的版本需要在基类中添加以下代码
        LinkedME.getInstance().onLMPaused(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        //兼容14之前的版本需要在基类中添加以下代码
        LinkedME.getInstance().onLMStoped(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        //兼容14之前的版本需要在基类中添加以下代码
        LinkedME.getInstance().onLMDestoryed(this);
        super.onDestroy();
    }


    //该方法需要添加
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

}
