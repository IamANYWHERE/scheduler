package com.toplyh.android.scheduler.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.app.App;
import com.toplyh.android.scheduler.service.presenter.HomePagePresenter;
import com.toplyh.android.scheduler.service.utils.SharedPreferencesUtils;

public class SplashActivity extends BaseActivity {

    //去主页
    private static final int GO_HOME=0;

    //去登录页
    private static final int GO_LOGIN=1;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GO_HOME:
                    Intent intent=new Intent(SplashActivity.this, HomePageActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case GO_LOGIN:
                    Intent intent1=new Intent(SplashActivity.this,MaterialLoginActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void initView() {
        setContentView(R.layout.activity_splash);
        Log.e("george",SharedPreferencesUtils.contain(SplashActivity.this,getString(R.string.token))+"  token");
        if (SharedPreferencesUtils.contain(SplashActivity.this,getString(R.string.token))){
            mHandler.sendEmptyMessageDelayed(GO_HOME,0);
        }else {
            mHandler.sendEmptyMessageDelayed(GO_LOGIN,0);
        }
    }

    @Override
    protected void addToolbar() {

    }

    @Override
    protected String[] getNeedPermissions() {
        return new String[]{
            "android.permission.INTERNET","android.permission.WRITE_EXTERNAL_STORAGE","android.permission.VIBRATE"

        };
    }

    @Override
    protected void permissionGrantedSuccess() {

    }

    @Override
    protected void permissionGrantedFail() {

    }
}
