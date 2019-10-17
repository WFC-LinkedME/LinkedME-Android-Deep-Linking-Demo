package com.microquation.linkedme.demo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.microquation.linkedme.android.LinkedME;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * <p>在自定义的Application的onCreate()方法中调用以下方法</p>
 *
 * <p>Created by qipo on 15/11/29.</p>
 */
public class LinkedMEDemoApp extends Application {

    private static LinkedMEDemoApp instance;
    /**
     * APP是否在后台
     */
    private boolean isInBackground = false;

    /**
     * 是否已经显示了广告
     */
    private boolean showedAd = false;

    public boolean isShowedAd() {
        return showedAd;
    }

    public void setShowedAd(boolean showedAd) {
        this.showedAd = showedAd;
    }


    public static LinkedMEDemoApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // 初始化SDK
        long currentTime = System.currentTimeMillis();
        LinkedME.getInstance(this, "7e289a2484f4368dbafbd1e5c7d06903");
        System.out.println("耗时====" + (System.currentTimeMillis() - currentTime));

        if (BuildConfig.DEBUG) {
            //设置debug模式下打印LinkedME日志
            LinkedME.getInstance().setDebug();
        }

        // 设置是否开启自动跳转指定页面，默认为true
        // 若在此处设置为false，请务必在配置Uri scheme的Activity页面的onResume()方法中，
        // 重新设置为true，否则将禁止开启自动跳转指定页面功能
        LinkedME.getInstance().setImmediate(false);

        //设置处理跳转逻辑的中转页
        LinkedME.getInstance().setHandleActivity(MiddleActivity.class.getName());


        //友盟社会化分享
        {
            UMConfigure.init(this, "560fce13e0f55a730c003844", "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0

            //微信
            PlatformConfig.setWeixin("wx6fc47eae6872f04c", "d4624c36b6795d1d99dcf0547af5443d");
            //新浪微博
            PlatformConfig.setSinaWeibo("2929366075", "b84a93ea3d2b89f04559eddb5663c809", "http://sns.whalecloud.com");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            CustomActivityLifeCycleObserver activityLifeCycleObserver = new CustomActivityLifeCycleObserver();
            unregisterActivityLifecycleCallbacks(activityLifeCycleObserver);
            registerActivityLifecycleCallbacks(activityLifeCycleObserver);
        }

    }

    public boolean isInBackground() {
        return isInBackground;
    }

    public void setInBackground(boolean inBackground) {
        isInBackground = inBackground;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private class CustomActivityLifeCycleObserver implements ActivityLifecycleCallbacks {

        private int activityCount = 0;
        private int activityInstanceCount = 0;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            activityInstanceCount++;
        }

        @Override
        public void onActivityStarted(Activity activity) {
            activityCount++;
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            activityCount--;
            if (activityCount < 1) {
                isInBackground = true;
                if (!TextUtils.equals(activity.getClass().getSimpleName(), AdvertisementActivity.class.getSimpleName())) {
                    showedAd = false;
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            activityInstanceCount--;
            if (activityInstanceCount < 1) {
                isInBackground = false;
            }
        }
    }

}
