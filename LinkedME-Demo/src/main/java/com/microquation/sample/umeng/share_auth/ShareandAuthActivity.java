package com.microquation.sample.umeng.share_auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.microquation.sample.R;
import com.umeng.socialize.utils.DeviceConfig;
import com.umeng.socialize.utils.Log;





/**
 * Created by umeng on 15/9/14.
 */
public class ShareandAuthActivity extends Activity{
    private Button shareButton,authbutton,userbutton;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String deviceId = DeviceConfig.getDeviceId(ShareandAuthActivity.this);
            if (view.getId() == R.id.umeng_share){
                Intent intent = new Intent(ShareandAuthActivity.this, ShareActivity.class);
                startActivity(intent);
            }else if (view.getId() == R.id.umeng_auth){
                Intent intent = new Intent(ShareandAuthActivity.this, AuthActivity.class);
                startActivity(intent);
            }else if (view.getId() == R.id.umeng_userinfo){
                Intent intent = new Intent(ShareandAuthActivity.this, UserinfoActivity.class);
                startActivity(intent);
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_share_auth);
        authbutton = (Button)findViewById(R.id.umeng_auth);
        shareButton = (Button)findViewById(R.id.umeng_share);
        userbutton = (Button)findViewById(R.id.umeng_userinfo);

        authbutton.setOnClickListener(clickListener);
        shareButton.setOnClickListener(clickListener);
        userbutton.setOnClickListener(clickListener);

    }
}
