package com.toplyh.android.scheduler.service.presenter;

import android.content.Context;
import android.content.Intent;

import com.toplyh.android.scheduler.service.manager.RemoteManager;
import com.toplyh.android.scheduler.service.view.View;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 我 on 2017/12/4.
 */

public class BasePresenter<V> implements Presenter<V>{

    public V mvpView;
    protected RemoteManager mRemoteManager;
    private CompositeSubscription mCompositeSubscription;
    protected Context mContext;

    public BasePresenter(Context context){
        this.mContext=context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }
    @Override
    public void pause() {

    }

    @Override
    public void attachView(V view) {
        this.mvpView=view;
        mRemoteManager=new RemoteManager(mContext);

    }

    @Override
    public void detachView() {
        this.mvpView=null;
        onUnsubscribe();
    }

    @Override
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    @Override
    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription==null){
            mCompositeSubscription=new CompositeSubscription();
        }

        mCompositeSubscription.add(observable
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber));
    }
}
