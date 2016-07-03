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
import android.view.Menu;
import android.view.MenuItem;
import com.microquation.linkedme.android.LinkedME;
import com.microquation.linkedme.android.callback.LMReferralCloseListener;
import com.microquation.linkedme.android.callback.LMSimpleInitListener;
import com.microquation.linkedme.android.indexing.LMUniversalObject;
import com.microquation.linkedme.android.referral.LMError;
import com.microquation.linkedme.android.util.LinkProperties;
import com.microquation.sample.R;

import java.util.HashMap;

/**
 * Welcome to the world of Android Market licensing. We're so glad to have you
 * onboard!
 * <p>
 * The first thing you need to do is get your hands on your public key.
 * Update the BASE64_PUBLIC_KEY constant below with your encoded public key,
 * which you can find on the
 * <a href="http://market.android.com/publish/editProfile">Edit Profile</a>
 * page of the Market publisher site.
 * <p>
 * Log in with the same account on your Cupcake (1.5) or higher phone or
 * your FroYo (2.2) emulator with the Google add-ons installed. Change the
 * test response on the Edit Profile page, press Save, and see how this
 * application responds when you check your license.
 * <p>
 * After you get this sample running, peruse the
 * <a href="http://developer.android.com/guide/publishing/licensing.html">
 * licensing documentation.</a>
 */
public class MainActivity extends AppCompatActivity {

    private LinkedME linkedME;

    private AppCompatImageButton id_apps, id_features, id_demo, id_intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
        //router();
    }

//    modify the cookie
    private void findViews() {
        id_apps = (AppCompatImageButton) findViewById(R.id.id_apps);
        id_features = (AppCompatImageButton) findViewById(R.id.id_features);
        id_demo = (AppCompatImageButton) findViewById(R.id.id_demo);
        id_intro = (AppCompatImageButton) findViewById(R.id.id_intro);
        id_apps.setOnClickListener(v -> startActivity(AppsActivity.newIntent(this)));
        id_features.setOnClickListener(v -> startActivity(FeaturesActivity.newIntent(this)));
        id_demo.setOnClickListener(v -> startActivity(DemoActivity.newIntent(this)));
        id_intro.setOnClickListener(v -> startActivity(SummaryActivity.newIntent(this)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        //初始化LinkedME实例
        linkedME = LinkedME.getInstance(this);
        //初始化Session，获取Intent内容及跳转参数
        linkedME.initSession(simpleInitListener, this.getIntent().getData(), this);

    }

    //解析深度链获取跳转参数，开发者自己实现参数相对应的页面内容。
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

                    //router
                    HashMap<String, String> hashMap = linkProperties.getControlParams();
                    String view = hashMap.get("View").toString();
                    if (view.equals("Partner")) {
                        Intent intent = AppsActivity.newIntent(MainActivity.this);
                        startActivity(intent);
                    } else if (view.equals("Feature")) {
                        startActivity(FeaturesActivity.newIntent(MainActivity.this));
                    } else if (view.equals("Demo")) {
                        startActivity(DemoActivity.newIntent(MainActivity.this));
                    } else if (view.equals("Summary")) {
                        startActivity(SummaryActivity.newIntent(MainActivity.this));
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
