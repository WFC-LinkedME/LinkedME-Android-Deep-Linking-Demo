package com.microquation.linkedme.demo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SP帮助类
 * Created by LinkedME06 on 16/11/23.
 */

public class SPHelper {

    private Context context;
    private static SPHelper prefHelper;
    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor prefsEditor;

    public SPHelper() {
    }

    private SPHelper(Context context) {
        sharedPreferences = context.getSharedPreferences("user_info",
                Context.MODE_PRIVATE);
        prefsEditor = sharedPreferences.edit();
        this.context = context;
    }

    public static SPHelper getInstance(Context context) {
        if (prefHelper == null) {
            prefHelper = new SPHelper(context);
        }
        return prefHelper;
    }

    public void setUserLogin(boolean isUserLogin){
        prefsEditor.putBoolean("isUserLogin", isUserLogin).commit();
    }

    public boolean getUserLogin(){
       return sharedPreferences.getBoolean("isUserLogin", false);
    }
}
