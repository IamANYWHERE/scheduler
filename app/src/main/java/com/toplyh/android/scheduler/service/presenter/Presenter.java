package com.toplyh.android.scheduler.service.presenter;

import android.content.Intent;

import com.toplyh.android.scheduler.service.view.View;


/**
 * Created by 我 on 2017/11/15.
 */

public interface Presenter {

    void onCreate();

    void onStart();

    void onStop();

    void pause();

    void attachView(View view);

    void attachIncomingIntent(Intent intent);
}
