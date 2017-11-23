package com.toplyh.android.scheduler.service.presenter;

import android.content.Context;
import android.content.Intent;

import com.toplyh.android.scheduler.service.entity.User;
import com.toplyh.android.scheduler.service.entity.state.LoginState;
import com.toplyh.android.scheduler.service.manager.DataManager;
import com.toplyh.android.scheduler.service.view.LoginView;
import com.toplyh.android.scheduler.service.view.View;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 我 on 2017/11/23.
 */

public class LoginPresenter implements Presenter {

    private DataManager manager;

    private CompositeSubscription mCompositeSubscription;

    private Context mContext;

    private LoginView mLoginView;

    private LoginState mState;

    public LoginPresenter(Context context){
        this.mContext=context;
    }


    @Override
    public void onCreate() {
        manager=new DataManager(mContext);
        mCompositeSubscription=new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void attachView(View view) {
        mLoginView=(LoginView)view;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void LoginUser(User user){
        mCompositeSubscription.add(manager.loginUser(user)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<LoginState>() {
            @Override
            public void onCompleted() {
                if (mState!=null){
                    mLoginView.OnSuccess(mState);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mLoginView.onError("请求失败");
            }

            @Override
            public void onNext(LoginState state) {
                mState=state;
            }
        }));
    }
}
