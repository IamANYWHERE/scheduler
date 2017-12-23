package com.toplyh.android.scheduler.app;

import android.app.Application;

import com.toplyh.android.scheduler.service.entity.MyObjectBox;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;
import io.objectbox.android.BuildConfig;

/**
 * Created by 我 on 2017/11/25.
 */

public class App extends Application {

    public static final String TAG="ObjectBoxExample";

    public static final boolean EXTERNAL_DIR=false;

    private BoxStore mBoxStore;


    @Override
    public void onCreate() {
        super.onCreate();
        mBoxStore= MyObjectBox.builder().androidContext(App.this).build();
        if (BuildConfig.DEBUG){
            new AndroidObjectBrowser(mBoxStore).start(this);
        }
    }

    public BoxStore getBoxStore(){
        return mBoxStore;

    }
}
