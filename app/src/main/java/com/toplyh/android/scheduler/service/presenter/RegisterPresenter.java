package com.toplyh.android.scheduler.service.presenter;

import android.content.Context;
import android.content.Intent;

import com.toplyh.android.scheduler.service.entity.state.RegisterState;
import com.toplyh.android.scheduler.service.entity.User;
import com.toplyh.android.scheduler.service.manager.DataManager;
import com.toplyh.android.scheduler.service.view.RegisterView;
import com.toplyh.android.scheduler.service.view.View;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 我 on 2017/11/23.
 */

public class RegisterPresenter implements Presenter {

    private DataManager manager;

    private CompositeSubscription mCompositeSubscription;

    private Context mContext;

    private RegisterView mRegisterView;

    private RegisterState mRegisterState;

    public RegisterPresenter(Context context){
        this.mContext=context;
    }
    @Override
    public void onCreate() {
        this.manager=new DataManager(mContext);
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
        mRegisterView =(RegisterView)view;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void registerUser(User user){
        mCompositeSubscription.add(manager.registerUser(user)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<RegisterState>() {
            @Override
            public void onCompleted() {
                if (mRegisterState !=null){
                    mRegisterView.OnSuccess(mRegisterState);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mRegisterView.onError("请求失败！");
            }

            @Override
            public void onNext(RegisterState registerState) {
                mRegisterState = registerState;
            }
        }));
    }
}
