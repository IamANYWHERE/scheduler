package com.toplyh.android.scheduler.service.manager;

import android.content.Context;

import com.toplyh.android.scheduler.service.RetrofitHelper;
import com.toplyh.android.scheduler.service.RetrofitService;
import com.toplyh.android.scheduler.service.entity.remote.Count;
import com.toplyh.android.scheduler.service.entity.remote.HttpsResult;
import com.toplyh.android.scheduler.service.entity.state.Token;

import rx.Observable;

/**
 * Created by 我 on 2017/11/15.
 */

public class RemoteManager {
    private RetrofitService mRetrofitService;

    public RemoteManager(Context context){
        mRetrofitService= RetrofitHelper.getInstance(context).getServer();
    }

    public Observable<HttpsResult<String>> registerUser(Count count){
        return mRetrofitService.registerUser(count);
    }

    public Observable<HttpsResult<Token>> loginUser(Count count){
        return mRetrofitService.loginUser(count);
    }
}
