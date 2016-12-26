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
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.microquation.linkedme.android.LinkedME;
import com.microquation.sample.R;

/**
 * <p>此Activity是为了演示当从深度链接跳转到intent-filter对应的Activity时,
 * 如何通过深度链接获取自定义参数,以便后续操作的实现(例如:跳转到指定的分享页面)</p>
 * <p>
 * <p>集成步骤:</p>
 * <p>1、在onStart()方法中获取LinkedME对象并调用 initSession(LMUniversalReferralInitListener callback, @NonNull Uri data, Activity activity)设置session进行监听</p>
 * <p>2、在onStop()方法中调用closeSession关闭当前session</p>
 */
public class MainActivity extends AppCompatActivity {

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
    private ImageView more;
    //针对跳转是否受用户登录限制的情况，不区分则无需此参数
    private boolean newIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
        //此处针对跳转是否受用户登录限制的情况
        if (SPHelper.getInstance(getApplicationContext()).getUserLogin()){
            //已登录用户可以跳转到分享页面
            LinkedME.getInstance().setImmediate(true);
        }else {
            //未登录用户不跳转到分享页面，而是跳转到登录页面，登录成功后跳转到分享页面
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //此处针对跳转是否受用户登录限制的情况
        if (SPHelper.getInstance(getApplicationContext()).getUserLogin()){
            //已登录用户可以跳转到分享页面
            LinkedME.getInstance().setImmediate(true);
        }else if (newIntent){
            //未登录用户不跳转到分享页面，而是跳转到登录页面，登录成功后跳转到分享页面
            //未登录用户不自动跳转
            LinkedME.getInstance().setImmediate(false);
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 此处针对跳转是否受用户登录限制的情况，在此处重置为false，防止重复跳转
        newIntent = false;
    }

    // 添加此处目的是针对后台APP通过uri scheme唤起的情况，
    // 注意：即使不区分用户是否登录也需要添加此设置，也可以添加到基类中
    @Override
    protected void onNewIntent(Intent intent) {
        newIntent = true;
        setIntent(intent);
    }

    /**
     * 组件初始化及事件响应
     */
    private void findViews() {
        id_apps = (AppCompatImageButton) findViewById(R.id.id_apps);
        id_features = (AppCompatImageButton) findViewById(R.id.id_features);
        id_demo = (AppCompatImageButton) findViewById(R.id.id_demo);
        id_intro = (AppCompatImageButton) findViewById(R.id.id_intro);
        more = (ImageView) findViewById(R.id.more);
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
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(more);
            }
        });
    }

    public void openActivity(String title, String param_view, String shareContent, String url_path, Class clazz) {
        Intent intent = new Intent(this, clazz);
        if (!TextUtils.isEmpty(title)) {
            intent.putExtra(ShareActivity.TITLE, title);
        }
        if (!TextUtils.isEmpty(param_view)) {
            intent.putExtra(ShareActivity.PARAM_VIEW, param_view);
        }
        if (!TextUtils.isEmpty(shareContent)) {
            intent.putExtra(ShareActivity.SHARE_CONTENT, shareContent);
        }
        if (!TextUtils.isEmpty(url_path)) {
            intent.putExtra(ShareActivity.URL_PATH, url_path);
        }
        startActivity(intent);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.login:
                        if (SPHelper.getInstance(getApplicationContext()).getUserLogin()){
                            Toast.makeText(MainActivity.this, "已登录！", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        return true;
                    case R.id.logout:
                        if (SPHelper.getInstance(getApplicationContext()).getUserLogin()){
                            SPHelper.getInstance(getApplicationContext()).setUserLogin(false);
                            // 此处针对跳转是否受用户登录限制的情况，在此处重置为false，防止用户点击退出后退到后台再唤起APP时跳转到详情页
                            LinkedME.getInstance().setImmediate(false);
                            Toast.makeText(MainActivity.this, "退出成功！", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "未登录，无需退出！", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });

        MenuInflater inflater = popup.getMenuInflater();
        if (SPHelper.getInstance(getApplicationContext()).getUserLogin()){
            inflater.inflate(R.menu.menu_main_login, popup.getMenu());
        }else {
            inflater.inflate(R.menu.menu_main, popup.getMenu());
        }
        popup.show();
    }

}
