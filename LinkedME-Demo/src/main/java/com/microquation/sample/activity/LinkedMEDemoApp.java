package com.microquation.sample.activity;

import android.app.Application;

import com.microquation.linkedme.android.LinkedME;
import com.microquation.linkedme.android.referral.LMUtil;
import com.umeng.socialize.PlatformConfig;

/**
 *
 * <p>在自定义的Application的onCreate()方法中调用以下方法</p>
 *  <pre class="prettyprint">
 * if (!LMUtil.isTestModeEnabled(this)) {
 *   LinkedME.getInstance(this);
 * } else {
 *   LinkedME.getTestInstance(this);
 * }
 * </pre>
 *
 * <p>Created by qipo on 15/11/29.</p>
 */
public class LinkedMEDemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (!LMUtil.isTestModeEnabled(this)) {
            LinkedME.getInstance(this);
        } else {
            LinkedME.getTestInstance(this);
        }
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
