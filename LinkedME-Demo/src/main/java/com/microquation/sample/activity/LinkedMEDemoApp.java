package com.microquation.sample.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.microquation.linkedme.android.LinkedME;
import com.microquation.sample.BuildConfig;
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

    public boolean isShowedAd() {
        return showedAd;
    }

    public void setShowedAd(boolean showedAd) {
        this.showedAd = showedAd;
    }

    /**
     * 是否已经显示了广告
     */
    private boolean showedAd = false;

    public static LinkedMEDemoApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Log.i("LinkedME", "onCreate: LinkedMEDemoApp............");

        // 初始化SDK
        LinkedME.getInstance(this);

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
            //微信
            PlatformConfig.setWeixin("wx6fc47eae6872f04c", "d4624c36b6795d1d99dcf0547af5443d");
            //新浪微博
            PlatformConfig.setSinaWeibo("2929366075", "b84a93ea3d2b89f04559eddb5663c809");
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
