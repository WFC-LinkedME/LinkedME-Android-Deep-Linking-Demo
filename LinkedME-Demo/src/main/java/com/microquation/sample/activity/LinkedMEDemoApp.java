package com.microquation.sample.activity;

import android.app.Application;

import com.microquation.linkedme.android.LinkedME;
import com.microquation.sample.BuildConfig;
import com.umeng.socialize.PlatformConfig;

/**
 * <p>在自定义的Application的onCreate()方法中调用以下方法</p>
 * <pre class="prettyprint">
 * if (!LMUtil.isTestModeEnabled(this)) {
 * LinkedME.getInstance(this);
 * } else {
 * LinkedME.getTestInstance(this);
 * }
 * </pre>
 * <p/>
 * <p>Created by qipo on 15/11/29.</p>
 */
public class LinkedMEDemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {

//          SDK 1.0.3及之后版本按照以下配置
            if (BuildConfig.DEBUG){
                //设置debug模式下打印LinkedME日志
                LinkedME.getInstance(this).setDebug();
            }else{
                LinkedME.getInstance(this);
            }

//            SDK 1.0.3 之前版本(不包含 1.0.3)按照以下配置
//            if (!LMUtil.isTestModeEnabled(this)) {
//                LinkedME.getInstance(this);
//            } else {
//                LinkedME.getTestInstance(this);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //友盟社会化分享
        {
            //微信
            PlatformConfig.setWeixin("wx6fc47eae6872f04c", "d4624c36b6795d1d99dcf0547af5443d");
            //新浪微博
            PlatformConfig.setSinaWeibo("2929366075", "b84a93ea3d2b89f04559eddb5663c809");
        }
    }
}
