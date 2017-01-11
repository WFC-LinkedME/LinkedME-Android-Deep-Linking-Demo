package com.microquation.sample.activity;

import android.app.Application;
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

    @Override
    public void onCreate() {
        super.onCreate();
            Log.i("LinkedME", "onCreate: LinkedMEDemoApp............");
            if (BuildConfig.DEBUG) {
                //设置debug模式下打印LinkedME日志
                LinkedME.getInstance(this).setDebug();
            } else {
                LinkedME.getInstance(this);
            }
            // 设置是否开启自动跳转指定页面，默认为true
            // 若在此处设置为false，请务必在配置Uri scheme的Activity页面的onCreate()方法中，
            // 重新设置为true，否则将禁止开启自动跳转指定页面功能
            // 示例：
            // public class MainActivity extends AppCompatActivity {
            // ...
            // @Override
            // protected void onCreate(Bundle savedInstanceState) {
            //    super.onCreate(savedInstanceState);
            //    setContentView(R.layout.main);
            //    LinkedME.getInstance().setImmediate(true);
            //   }
            // ...
            //  }
            LinkedME.getInstance().setImmediate(false);
        //友盟社会化分享
        {
            //微信
            PlatformConfig.setWeixin("wx6fc47eae6872f04c", "d4624c36b6795d1d99dcf0547af5443d");
            //新浪微博
            PlatformConfig.setSinaWeibo("2929366075", "b84a93ea3d2b89f04559eddb5663c809");
        }
    }
}
