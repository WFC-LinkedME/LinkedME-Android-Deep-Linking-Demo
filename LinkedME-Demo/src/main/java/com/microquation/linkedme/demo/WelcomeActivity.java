package com.microquation.linkedme.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.microquation.linkedme.android.LinkedME;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * 欢迎页面 Created by LinkedME06 on 16/11/23.
 */

public class WelcomeActivity extends BaseActivity {

    private TextView down_timer;
    //计时器
    private CountDownTimer timer;
    //判断计时器是否被取消
    private boolean isTimerCanceled;

    private AtomicBoolean isPrivacyShowed = new AtomicBoolean(false);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        // 欢迎页面也是广告页面
        LinkedMEDemoApp.getInstance().setShowedAd(true);

        startCountDownTime(5);
        Button open_demo = (Button) findViewById(R.id.open_demo);
        Button show_ad = (Button) findViewById(R.id.show_ad);
        down_timer = (TextView) findViewById(R.id.down_timer);
        open_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDemo();
            }
        });
        show_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                Intent intent = new Intent(WelcomeActivity.this, AdDetailActivity.class);
                Intent[] stackIntents = new Intent[]{mainIntent, intent};
                ContextCompat.startActivities(WelcomeActivity.this, stackIntents);
                finish();
            }
        });
        //微信中通过应用宝唤起退台后台的APP
        Log.d(getClass().getSimpleName(), "onCreate: isTimerCanceled=" + isTimerCanceled);
        if (!SPHelper.getInstance(getApplicationContext()).getPrivacy()) {
            if (!isPrivacyShowed.getAndSet(true)) {
                showPrivacy();
            }
        }
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
        if (!SPHelper.getInstance(getApplicationContext()).getPrivacy()) {
            if (!isPrivacyShowed.getAndSet(true)) {
                showPrivacy();
            }
        } else {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        Log.d(getClass().getSimpleName(), "onResume: isTimerCanceled=" + isTimerCanceled + "timer=" + timer);
        //此处判断timer==null是为了解决应用宝唤起后台APP后，有时isTimerCanceled一直为false的情况
        if (timer == null || isTimerCanceled) {
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

    private void showPrivacy() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append("服务协议和隐私政策" +
                "请你务必审慎阅读、充分理解\"服务协议\"和\"隐私政策\"各条款," +
                "包括但不限于:为了向你提供深度链接、内容分享等服务,我们需要收集你的设备信息、操作日志等个人信息。" +
                "你可以在\"系统设置\"中查看、变更、删除个人信息并管理你的授权，" +
                "你可阅读《服务协议》和《隐私政策》 了解详细信息。如你同意,请点\"同意\"开始接受我们的服务。");
        ClickableSpan privacyZeroClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(WelcomeActivity.this, PrivacyActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
            }

            //去除连接下划线
            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(ContextCompat.getColor(WelcomeActivity.this, R.color.color_content_green));
                textPaint.setUnderlineText(false);
            }
        };
        spannableStringBuilder.setSpan(privacyZeroClickableSpan, 123, 129, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ClickableSpan privacyOneClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(WelcomeActivity.this, PrivacyActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            }

            //去除连接下划线
            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(ContextCompat.getColor(WelcomeActivity.this, R.color.color_content_green));
                textPaint.setUnderlineText(false);
            }
        };
        spannableStringBuilder.setSpan(privacyOneClickableSpan, 130, 136, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView textView = new TextView(WelcomeActivity.this);
        textView.setText(spannableStringBuilder);
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics());
        textView.setPadding(padding, padding, padding, 0);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        builder.setView(textView);
        builder.setPositiveButton("同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SPHelper.getInstance(getApplicationContext()).setPrivacy(true);
                LinkedME.getInstance().setPrivacyStatus(true);
                dialog.dismiss();
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).setNegativeButton("暂不使用", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(-1);
            }
        });
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
