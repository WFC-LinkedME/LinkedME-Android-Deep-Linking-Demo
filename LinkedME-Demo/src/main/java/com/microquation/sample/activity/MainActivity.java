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

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;

import com.microquation.sample.R;

/**
 * <p>此Activity是为了演示当从深度链接跳转到intent-filter对应的Activity时,
 * 如何通过深度链接获取自定义参数,以便后续操作的实现(例如:跳转到指定的分享页面)</p>
 * <p>
 * <p>集成步骤:</p>
 * <p>1、在onStart()方法中获取LinkedME对象并调用 initSession(LMUniversalReferralInitListener callback, @NonNull Uri data, Activity activity)设置session进行监听</p>
 * <p>2、在onStop()方法中调用closeSession关闭当前session</p>
 */
public class MainActivity extends BaseActivity {

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
                openActivity(getString(R.string.str_apps_name), getString(R.string.str_h5_apps), getString(R.string.str_share_content_apps), getString(R.string.str_path_apps), ShareActivity.class);
            }
        });
        id_features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(getString(R.string.str_features_name), getString(R.string.str_h5_features), getString(R.string.str_share_content_features), getString(R.string.str_path_features), ShareActivity.class);
            }
        });
        id_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(null, null, null, null, DemoActivity.class);
            }
        });
        id_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(getString(R.string.str_intro_name), getString(R.string.str_h5_intro), getString(R.string.str_share_content_intro), getString(R.string.str_path_intro), ShareActivity.class);
            }
        });
    }

}
