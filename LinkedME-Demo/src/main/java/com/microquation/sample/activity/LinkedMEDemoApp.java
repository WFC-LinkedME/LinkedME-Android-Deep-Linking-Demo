package com.microquation.sample.activity;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.microquation.linkedme.android.LinkedME;
import com.microquation.linkedme.android.referral.LMApp;
import com.microquation.linkedme.android.referral.LMUtil;
import com.umeng.socialize.PlatformConfig;

/**
 * Created by qipo on 15/11/29.
 */
public class LinkedMEDemoApp extends LMApp {


    //修改LMApp，原来为
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        if (!LMUtil.isTestModeEnabled(this)) {
//            LinkedME.getInstance(this);
//        } else {
//            LinkedME.getTestInstance(this);
//        }
//        LinkedME.initialize(this);

        //友盟社会化分享
        {
            //微信
            PlatformConfig.setWeixin("wx6fc47eae6872f04c", "d4624c36b6795d1d99dcf0547af5443d");
            //新浪微博
            PlatformConfig.setSinaWeibo("2929366075", "b84a93ea3d2b89f04559eddb5663c809");
        }
    }
}
