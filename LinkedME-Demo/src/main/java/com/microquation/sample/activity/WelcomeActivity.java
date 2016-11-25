package com.microquation.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.microquation.sample.R;

/**
 * 欢迎页面
 * Created by LinkedME06 on 16/11/23.
 */

public class WelcomeActivity extends AppCompatActivity {

    private TextView down_timer;
    //计时器
    private CountDownTimer timer;
    //判断计时器是否被取消
    private boolean isTimerCanceled;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        startCountDownTime(5);
        Button open_demo = (Button) findViewById(R.id.open_demo);
        down_timer = (TextView) findViewById(R.id.down_timer);
        open_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDemo();
            }
        });
        //微信中通过应用宝唤起退台后台的APP
        Log.d(getClass().getSimpleName(), "onCreate: isTimerCanceled=" + isTimerCanceled);
    }

    private void startCountDownTime(long time) {
        timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //每隔countDownInterval秒会回调一次onTick()方法
                down_timer.setText(String.format(getString(R.string.count_down_str), millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                openDemo();
            }

        };
        timer.start();// 开始计时
    }

    private void openDemo() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        Log.d(getClass().getSimpleName(), "onResume: isTimerCanceled=" + isTimerCanceled + "timer=" + timer);
        //此处判断timer==null是为了解决应用宝唤起后台APP后，有时isTimerCanceled一直为false的情况
        if (timer == null || isTimerCanceled){
            //若被取消，则直接执行跳转
            openDemo();
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d(getClass().getSimpleName(), "onStop: isTimerCanceled=" + isTimerCanceled);
        timer.cancel();
        isTimerCanceled = true;
        super.onStop();
    }
}
