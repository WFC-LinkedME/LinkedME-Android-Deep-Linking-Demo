package com.microquation.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.microquation.sample.R;

/**
 * 启动页面
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        //以下是无法在application中初始化时可在launcher页面初始化 --start

//        if (BuildConfig.DEBUG) {
//            //设置debug模式下打印LinkedME日志
//            LinkedME.getInstance(this.getApplicationContext()).setDebug();
//        } else {
//            LinkedME.getInstance(this.getApplicationContext());
//        }
//        // 设置是否开启自动跳转指定页面，默认为true
//        // 若在此处设置为false，请务必在配置Uri scheme的Activity页面的onCreate()方法中，
//        // 重新设置为true，否则将禁止开启自动跳转指定页面功能
//        // 示例：
//        // public class MainActivity extends AppCompatActivity {
//        // ...
//        // @Override
//        // protected void onCreate(Bundle savedInstanceState) {
//        //    super.onCreate(savedInstanceState);
//        //    setContentView(R.layout.main);
//        //    LinkedME.getInstance().setImmediate(true);
//        //   }
//        // ...
//        //  }
//        LinkedME.getInstance().setImmediate(false);

        //以上是无法在application中初始化时可在launcher页面初始化 --end


        super.onCreate(savedInstanceState);
        Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }
}
