package com.toplyh.android.scheduler.service.manager;

import android.content.Context;

import com.toplyh.android.scheduler.service.RetrofitHelper;
import com.toplyh.android.scheduler.service.RetrofitService;
import com.toplyh.android.scheduler.service.entity.remote.Count;
import com.toplyh.android.scheduler.service.entity.remote.HttpsResult;
import com.toplyh.android.scheduler.service.entity.remote.MsgCount;
import com.toplyh.android.scheduler.service.entity.remote.PJS;
import com.toplyh.android.scheduler.service.entity.remote.Project;
import com.toplyh.android.scheduler.service.entity.remote.ProjectAndMember;
import com.toplyh.android.scheduler.service.entity.remote.Projects;
import com.toplyh.android.scheduler.service.entity.remote.UpdateCount;
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

    public Observable<HttpsResult<Projects>> getProjects(){
        return mRetrofitService.getProjects();
    }

    public Observable<HttpsResult<String>> newProject(ProjectAndMember projectAndMember){
        return mRetrofitService.newProject(projectAndMember);
    }

    public Observable<HttpsResult<String>> updateUser(UpdateCount updateCount){
        return mRetrofitService.updateUser(updateCount);
    }

    public Observable<HttpsResult<MsgCount>> getUserMsg(){
        return mRetrofitService.getUserMsg();
    }
}
