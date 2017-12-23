package com.toplyh.android.scheduler.service.presenter;

import android.content.Intent;

import com.toplyh.android.scheduler.service.view.View;

import rx.Observable;
import rx.Subscriber;


/**
 * Created by 我 on 2017/11/15.
 */

public interface Presenter<V>{

    void onCreate();

    void onStart();

    void pause();

    void attachView(V view);

    void detachView();

    void onUnsubscribe();

    void attachIncomingIntent(Intent intent);

    void addSubscription(Observable observable, Subscriber subscriber);
}
