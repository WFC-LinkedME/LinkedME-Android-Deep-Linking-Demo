package com.microquation.sample.umeng.share_auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.microquation.sample.R;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.view.UMFriendListener;

import java.util.Map;

/**
 * Created by wangfei on 15/10/10.
 */
public class UserinfoActivity extends Activity{
    private UMShareAPI mShareAPI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_user);
        mShareAPI = UMShareAPI.get(this);
    }
    public void onClick(View view) {
        SHARE_MEDIA platform = null;
        switch (view.getId()){
            case R.id.sinainfo:
                platform = SHARE_MEDIA.SINA;
                break;
            case R.id.qqinfo:
                platform = SHARE_MEDIA.QQ;
                break;
            case R.id.facebookinfo:
                platform = SHARE_MEDIA.FACEBOOK;
                break;
            case R.id.wxinfo:
                platform = SHARE_MEDIA.WEIXIN;
                break;
            case R.id.getfriend:
                break;

        }
        mShareAPI.getPlatformInfo(UserinfoActivity.this, platform, umAuthListener);

    }
    public void getFriendbyClick(View view) {

        mShareAPI.getFriend(UserinfoActivity.this, SHARE_MEDIA.SINA, umGetfriendListener);

    }
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            if (data!=null){
                Log.d("auth callbacl","getting data");
                Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "get fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "get cancel", Toast.LENGTH_SHORT).show();
        }
    };
    private UMFriendListener umGetfriendListener = new UMFriendListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, Object> data) {
            if (data!=null){
                Toast.makeText(getApplicationContext(), data.get("json").toString(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "get fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "get cancel", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }
}
