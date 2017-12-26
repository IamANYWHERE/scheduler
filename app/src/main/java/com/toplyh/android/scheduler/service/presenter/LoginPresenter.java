package com.toplyh.android.scheduler.service.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.ApiCallBack;
import com.toplyh.android.scheduler.service.entity.local.User;
import com.toplyh.android.scheduler.service.entity.remote.Count;
import com.toplyh.android.scheduler.service.entity.remote.HttpsResult;
import com.toplyh.android.scheduler.service.entity.state.Token;
import com.toplyh.android.scheduler.service.manager.RemoteManager;
import com.toplyh.android.scheduler.service.utils.SharedPreferencesUtils;
import com.toplyh.android.scheduler.service.view.LoginView;
import com.toplyh.android.scheduler.service.view.View;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 我 on 2017/11/23.
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginView mLoginView;

    public LoginPresenter(Context context,LoginView loginView){
        super(context);
        attachView(loginView);
        this.mLoginView=loginView;
    }





    public void loginUser(){
        final String userName=mLoginView.getUseName();
        String pwd=mLoginView.getPassword();

        if (userName==null||userName.equals("")||pwd==null||pwd.equals("")){
            mLoginView.userOrPwdIsNull();
            return;
        }

        mLoginView.showDialog();
        ApiCallBack<HttpsResult<Token>> subscriber=new ApiCallBack<HttpsResult<Token>>() {
            @Override
            public void onSuccess(HttpsResult<Token> model) {
                mLoginView.cancelDialog();
                if (model.getState()==100){
                    //closeRetrofit();
                    mLoginView.toMainActivity();
                    saveUserToken(model,userName);
                }else {
                    mLoginView.toastMessage("账号或密码错误！");
                }
            }

            @Override
            public void onFailure(String msg) {
                Log.e("dandy", "onFailure: "+msg );
                mLoginView.cancelDialog();
            }

            @Override
            public void onFinish() {

            }
        };

        Count count=new Count();
        count.setName(userName);
        count.setPassword(pwd);
        addSubscription(mRemoteManager.loginUser(count),subscriber);
    }

    private void saveUserToken(HttpsResult<Token> token,String username){
        SharedPreferencesUtils.setParam(mContext,
                mContext.getString(R.string.token)
                ,token.getData().getToken());
        SharedPreferencesUtils.setParam(mContext,
                mContext.getString(R.string.username),
                username);
    }

    public void destroy(){
        detachView();
    }
}
