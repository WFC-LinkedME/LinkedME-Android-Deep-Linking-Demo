package com.microquation.sample.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.microquation.linkedme.android.LinkedME;
import com.microquation.linkedme.android.callback.LMReferralCloseListener;
import com.microquation.linkedme.android.callback.LMSimpleInitListener;
import com.microquation.linkedme.android.indexing.LMUniversalObject;
import com.microquation.linkedme.android.referral.LMError;
import com.microquation.linkedme.android.util.LinkProperties;
import com.microquation.sample.R;

import java.util.HashMap;

/**
 * Created by LinkedME06 on 16/8/14.
 * 将深度链接处理集成到BaseActivity中,可解决应用后台唤起无法跳转到详情页的问题,具体参考demo中的"SDK集成注意事项.md"文件
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    private LinkedME linkedME;

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: " + this.getClass().getSimpleName());
        try {
            //如果消息未处理则会初始化initSession，因此不会每次都去处理数据，不会影响应用原有性能问题
            if (!LinkedME.getInstance().isHandleStatus()) {
                Log.i(TAG, "LinkedME +++++++ initSession... " + this.getClass().getSimpleName());
                //初始化LinkedME实例
                linkedME = LinkedME.getInstance();
                //初始化Session，获取Intent内容及跳转参数
                linkedME.initSession(simpleInitListener, this.getIntent().getData(), this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            try {
                Log.i(TAG, "开始处理deep linking数据... " + this.getClass().getSimpleName());
                if (error != null) {
                    Log.i("LinkedME-Demo", "LinkedME初始化失败. " + error.getMessage());
                } else {

                    //LinkedME SDK初始化成功，获取跳转参数，具体跳转参数在LinkProperties中，和创建深度链接时设置的参数相同；
                    Log.i("LinkedME-Demo", "LinkedME初始化完成");

                    if (linkProperties != null) {
                        Log.i("LinkedME-Demo", "Channel " + linkProperties.getChannel());
                        Log.i("LinkedME-Demo", "control params " + linkProperties.getControlParams());
                        Log.i("LinkedME-Demo", "link(深度链接) " + linkProperties.getLMLink());
                        Log.i("LinkedME-Demo", "是否为新安装 " + linkProperties.isLMNewUser());
                        //获取自定义参数封装成的hashmap对象
                        HashMap<String, String> hashMap = linkProperties.getControlParams();

                        //获取传入的参数
                        String view = hashMap.get("View");
                        String title = "";
                        String shareContent = "";
                        String url_path = "";
                        if (view != null) {
                            if (view.equals("Demo")) {
                                Intent intent = new Intent(BaseActivity.this, DemoActivity.class);
                                intent.putExtra("keyValue", hashMap.toString());
                                startActivity(intent);
                                return;
                            } else if (view.equals(getString(R.string.str_h5_apps))) {
                                title = getString(R.string.str_apps_name);
                                shareContent = getString(R.string.str_share_content_apps);
                                url_path = getString(R.string.str_path_apps);
                            } else if (view.equals(getString(R.string.str_h5_features))) {
                                title = getString(R.string.str_features_name);
                                shareContent = getString(R.string.str_share_content_features);
                                url_path = getString(R.string.str_path_features);
                            } else if (view.equals(getString(R.string.str_h5_intro))) {
                                title = getString(R.string.str_intro_name);
                                shareContent = getString(R.string.str_share_content_intro);
                                url_path = getString(R.string.str_path_intro);
                            }
                            openActivity(title, view, shareContent, url_path, ShareActivity.class);
                        }
                    }

                    if (lmUniversalObject != null) {
                        Log.i("LinkedME-Demo", "title " + lmUniversalObject.getTitle());
                        Log.i("LinkedME-Demo", "control " + linkProperties.getControlParams());
                        Log.i("ContentMetaData", "metadata " + lmUniversalObject.getMetadata());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: " + this.getClass().getSimpleName());
        if (linkedME != null) {
            linkedME.closeSession(new LMReferralCloseListener() {
                @Override
                public void onCloseFinish() {
                }
            });
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(TAG, "onNewIntent: " + this.getClass().getSimpleName());
        simpleInitListener.reset();
        setIntent(intent);
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
}
