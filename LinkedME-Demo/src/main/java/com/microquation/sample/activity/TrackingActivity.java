package com.microquation.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.microquation.linkedme.android.moniter.LMTracking;
import com.microquation.sample.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 追踪示例 Created by LinkedME06 on 16/11/4.
 */

public class TrackingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_activity);
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LMTracking.onRegister("lipeng");
            }
        });
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LMTracking.onLogin("lipeng");
            }
        });
        findViewById(R.id.pay_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.putOpt("名称", "商品一");
                    jsonObject.putOpt("单价", "123");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LMTracking.onPay("linkedme2016", "0132456789", jsonObject, "123", "linkedme");
            }
        });

        findViewById(R.id.custom_point).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.putOpt("属性1", "12345");
                    jsonObject.putOpt("属性2", "67890");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LMTracking.onCustEvent("事件二", jsonObject, "linkedme");
            }
        });


    }
}
