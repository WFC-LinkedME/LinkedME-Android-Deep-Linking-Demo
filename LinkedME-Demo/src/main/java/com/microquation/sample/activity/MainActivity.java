/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Created by qipo on 15/7/29.
 */

package com.microquation.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;

import com.microquation.linkedme.android.LinkedME;
import com.microquation.linkedme.android.callback.LMReferralCloseListener;
import com.microquation.linkedme.android.callback.LMSimpleInitListener;
import com.microquation.linkedme.android.indexing.LMUniversalObject;
import com.microquation.linkedme.android.referral.LMError;
import com.microquation.linkedme.android.util.LinkProperties;
import com.microquation.sample.R;

import java.util.HashMap;

/**
 * <p>此Activity是为了演示当从深度链接跳转到intent-filter对应的Activity时,
 * 如何通过深度链接获取自定义参数,以便后续操作的实现(例如:跳转到指定的分享页面)</p>
 * <p>
 * <p>集成步骤:</p>
 * <p>1、在onStart()方法中获取LinkedME对象并调用 initSession(LMUniversalReferralInitListener callback, @NonNull Uri data, Activity activity)设置session进行监听</p>
 * <p>2、在onStop()方法中调用closeSession关闭当前session</p>
 */
public class MainActivity extends AppCompatActivity {

    private LinkedME linkedME;
    /**
     * 应用方
     */
    private AppCompatImageButton id_apps;
    /**
     * 产品特点
     */
    private AppCompatImageButton id_features;
    /**
     * DEMO
     */
    private AppCompatImageButton id_demo;
    /**
     * 简介
     */
    private AppCompatImageButton id_intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
    }

    /**
     * 组件初始化及事件响应
     */
    private void findViews() {
        id_apps = (AppCompatImageButton) findViewById(R.id.id_apps);
        id_features = (AppCompatImageButton) findViewById(R.id.id_features);
        id_demo = (AppCompatImageButton) findViewById(R.id.id_demo);
        id_intro = (AppCompatImageButton) findViewById(R.id.id_intro);
        id_apps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AppsActivity.class));
            }
        });
        id_features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FeaturesActivity.class));
            }
        });
        id_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DemoActivity.class));
            }
        });
        id_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SummaryActivity.class));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        //初始化LinkedME实例

        linkedME = LinkedME.getInstance(this);
        //初始化Session，获取Intent内容及跳转参数
        linkedME.initSession(simpleInitListener, this.getIntent().getData(), this);
    }

    /**
     * <p>解析深度链获取跳转参数，开发者自己实现参数相对应的页面内容</p>
     * <p>通过LinkProperties对象调用getControlParams方法获取自定义参数的HashMap对象,
     * 通过创建的自定义key获取相应的值,用于数据处理。</p>
     */
    //。
    LMSimpleInitListener simpleInitListener = new LMSimpleInitListener() {
        @Override
        public void onSimpleInitFinished(LMUniversalObject lmUniversalObject, LinkProperties linkProperties, LMError error) {
            if (error != null) {
                Log.i("LinkedME-Demo", "LinkedME初始化失败. " + error.getMessage());
            } else {

                //LinkedME SDK初始化成功，获取跳转参数，具体跳转参数在LinkProperties中，和创建深度链接时设置的参数相同；
                Log.i("LinkedME-Demo", "LinkedME初始化完成");

                if (linkProperties != null) {
                    Log.i("LinkedME-Demo", "Channel " + linkProperties.getChannel());
                    Log.i("LinkedME-Demo", "control params " + linkProperties.getControlParams());

                    //获取自定义参数封装成的hashmap对象
                    HashMap<String, String> hashMap = linkProperties.getControlParams();
                    //获取传入的参数
                    String view = hashMap.get("View");
                    if (view != null) {
                        if (view.equals("Partner")) {
                            startActivity(new Intent(MainActivity.this, AppsActivity.class));
                        } else if (view.equals("Feature")) {
                            startActivity(new Intent(MainActivity.this, FeaturesActivity.class));
                        } else if (view.equals("Demo")) {
                            startActivity(new Intent(MainActivity.this, DemoActivity.class));
                        } else if (view.equals("Summary")) {
                            startActivity(new Intent(MainActivity.this, SummaryActivity.class));
                        }
                    }
                }

                if (lmUniversalObject != null) {
                    Log.i("LinkedME-Demo", "title " + lmUniversalObject.getTitle());
                    Log.i("LinkedME-Demo", "control " + linkProperties.getControlParams());
                    Log.i("ContentMetaData", "metadata " + lmUniversalObject.getMetadata());
                }
            }
        }
    };


    @Override
    public void onStop() {
        super.onStop();
        linkedME.closeSession(new LMReferralCloseListener() {
            @Override
            public void onCloseFinish() {
            }
        });
    }

}
