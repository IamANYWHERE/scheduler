package com.toplyh.android.scheduler.service;

import com.toplyh.android.scheduler.service.entity.state.LoginState;
import com.toplyh.android.scheduler.service.entity.state.RegisterState;
import com.toplyh.android.scheduler.service.entity.User;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 我 on 2017/11/15.
 */

public interface RetrofitService {

    @POST("user")
    Observable<RegisterState> registerUser(@Body User user);


    @POST("authentication")
    Observable<LoginState> loginUser(@Body User user);
}
