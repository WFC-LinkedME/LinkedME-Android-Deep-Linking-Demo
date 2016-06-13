package com.microquation.sample.umeng.share_auth;
/**
 * Created by wangfei on 15/9/14.
 */
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


import java.util.Map;

public class AuthActivity extends Activity{

        private UMShareAPI mShareAPI = null;

        public void onClickAuth(View view) {
            SHARE_MEDIA platform = null;
            if (view.getId() == R.id.app_auth_sina){
                platform = SHARE_MEDIA.SINA;
            }else if (view.getId() == R.id.app_auth_renren){
                platform = SHARE_MEDIA.RENREN;
            }else if (view.getId() == R.id.app_auth_douban){
                platform = SHARE_MEDIA.DOUBAN;
            }else if (view.getId() == R.id.app_auth_qq){
                platform = SHARE_MEDIA.QQ;
            }else if (view.getId() == R.id.app_auth_weixin){
                platform = SHARE_MEDIA.WEIXIN;
            }else if (view.getId() == R.id.app_auth_facebook){
                platform = SHARE_MEDIA.FACEBOOK;
            }else if (view.getId() == R.id.app_auth_twitter){
                platform = SHARE_MEDIA.TWITTER;
            }else if (view.getId() == R.id.app_auth_tencent){
                platform = SHARE_MEDIA.TENCENT;
            }else if(view.getId() == R.id.app_auth_linkedin){
                platform = SHARE_MEDIA.LINKEDIN;
            }
            /**begin invoke umeng api**/

            mShareAPI.doOauthVerify(AuthActivity.this, platform, umAuthListener);
        }

    public void onClickDeletAuth(View view) {
        SHARE_MEDIA platform = null;
        if (view.getId() == R.id.app_del_auth_sina){
            platform = SHARE_MEDIA.SINA;
        }else if (view.getId() == R.id.app_del_auth_renren){
            platform = SHARE_MEDIA.RENREN;
        }else if (view.getId() == R.id.app_del_auth_douban){
            platform = SHARE_MEDIA.DOUBAN;
        }else if (view.getId() == R.id.app_del_auth_qq){
            platform = SHARE_MEDIA.QQ;
        }else if (view.getId() == R.id.app_del_auth_weixin){
            platform = SHARE_MEDIA.WEIXIN;
        }else if (view.getId() == R.id.app_del_auth_facebook){
            platform = SHARE_MEDIA.FACEBOOK;
        }else if (view.getId() == R.id.app_del_auth_twitter){
            platform = SHARE_MEDIA.TWITTER;
        }else if(view.getId() == R.id.app_auth_linkedin_del){
            platform = SHARE_MEDIA.LINKEDIN;
        }
        /**begin invoke umeng api**/
        mShareAPI.deleteOauth(AuthActivity.this, platform, umdelAuthListener);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_auth);
        /** init auth api**/
        mShareAPI = UMShareAPI.get( this );
    }
    /** auth callback interface**/
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };
    /** delauth callback interface**/
    private UMAuthListener umdelAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "delete Authorize succeed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "delete Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "delete Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("auth", "on activity re 2");
        mShareAPI.onActivityResult(requestCode, resultCode, data);
        Log.d("auth", "on activity re 3");
    }
}
