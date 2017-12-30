package com.toplyh.android.scheduler.service;

import com.toplyh.android.scheduler.service.entity.remote.Count;
import com.toplyh.android.scheduler.service.entity.remote.HttpsResult;
import com.toplyh.android.scheduler.service.entity.remote.MsgCount;
import com.toplyh.android.scheduler.service.entity.remote.PJS;
import com.toplyh.android.scheduler.service.entity.remote.Project;
import com.toplyh.android.scheduler.service.entity.remote.ProjectAndMember;
import com.toplyh.android.scheduler.service.entity.remote.Projects;
import com.toplyh.android.scheduler.service.entity.remote.UpdateCount;
import com.toplyh.android.scheduler.service.entity.state.Token;

import java.util.List;

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
    Observable<HttpsResult<String>> registerUser(@Body Count count);

    @POST("user/update")
    Observable<HttpsResult<String>> updateUser(@Body UpdateCount updateCount);

    @GET("user/msg")
    Observable<HttpsResult<MsgCount>> getUserMsg();

    @POST("authentication")
    Observable<HttpsResult<Token>> loginUser(@Body Count count);

    @GET("project/all")
    Observable<HttpsResult<Projects>> getProjects();

    @POST("project/addProjectAndMember")
    Observable<HttpsResult<String>> newProject(@Body ProjectAndMember projectAndMember);


    @POST("hello-convert-and-send")
    Observable<Void> sendRestEcho(@Query("msg") String message);
}
