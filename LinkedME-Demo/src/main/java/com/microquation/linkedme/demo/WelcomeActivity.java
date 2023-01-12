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
        builder.setTitle("温馨提示");
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append("我们根据最新的监管要求更新了《隐私政策》，特向您说明如下：\n" +
                "1.为了向您提供基本的功能，我们会收集、使用必要的信息；\n" +
                "2.基于您的明示授权，我们可能会获取您的设备号信息：IMEI、IMSI、MAC地址、AndroidID等信息（以保障深链功能正常），读取剪切板内容（提高深链场景还原率），您有权拒绝或取消授权；\n" +
                "3.我们会采取业界先进的安全措施保护您的信息安全；\n" +
                "4.未经您同意，我们不会从第三方获取、共享或向其提供您的信息；");
        ClickableSpan privacyZeroClickableSpan = new ClickableSpan() {
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
        spannableStringBuilder.setSpan(privacyZeroClickableSpan, 14, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ClickableSpan privacyOneClickableSpan = new ClickableSpan() {
//            @Override
//            public void onClick(View widget) {
//                Intent intent = new Intent(WelcomeActivity.this, PrivacyActivity.class);
//                intent.putExtra("type", 1);
//                startActivity(intent);
//            }
//
//            //去除连接下划线
//            @Override
//            public void updateDrawState(TextPaint textPaint) {
//                textPaint.setColor(ContextCompat.getColor(WelcomeActivity.this, R.color.color_content_green));
//                textPaint.setUnderlineText(false);
//            }
//        };
//        spannableStringBuilder.setSpan(privacyOneClickableSpan, 130, 136, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

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
