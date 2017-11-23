package com.toplyh.android.scheduler.service.manager;

import android.content.Context;

import com.toplyh.android.scheduler.service.RetrofitHelper;
import com.toplyh.android.scheduler.service.RetrofitService;
import com.toplyh.android.scheduler.service.entity.state.LoginState;
import com.toplyh.android.scheduler.service.entity.state.RegisterState;
import com.toplyh.android.scheduler.service.entity.User;

import rx.Observable;

/**
 * Created by 我 on 2017/11/15.
 */

public class DataManager {
    private RetrofitService mRetrofitService;

    public DataManager(Context context){
        mRetrofitService= RetrofitHelper.getInstance(context).getServer();
    }

    public Observable<RegisterState> registerUser(User user){
        return mRetrofitService.registerUser(user);
    }

    public Observable<LoginState> loginUser(User user){
        return mRetrofitService.loginUser(user);
    }
}
