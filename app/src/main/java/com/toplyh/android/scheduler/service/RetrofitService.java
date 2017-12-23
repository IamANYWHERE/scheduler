package com.toplyh.android.scheduler.service;

import com.toplyh.android.scheduler.service.entity.remote.Count;
import com.toplyh.android.scheduler.service.entity.remote.HttpsResult;
import com.toplyh.android.scheduler.service.entity.state.Token;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 我 on 2017/11/15.
 */

public interface RetrofitService {

    @POST("user")
    Observable<HttpsResult<String>> registerUser(@Body Count count);


    @POST("authentication")
    Observable<HttpsResult<Token>> loginUser(@Body Count count);

    @POST("hello-convert-and-send")
    Observable<Void> sendRestEcho(@Query("msg") String message);
}
