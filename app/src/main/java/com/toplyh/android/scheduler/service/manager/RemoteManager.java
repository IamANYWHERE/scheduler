package com.toplyh.android.scheduler.service.manager;

import android.content.Context;
import android.content.Intent;

import com.toplyh.android.scheduler.service.RetrofitHelper;
import com.toplyh.android.scheduler.service.RetrofitService;
import com.toplyh.android.scheduler.service.entity.remote.AddSprint;
import com.toplyh.android.scheduler.service.entity.remote.Count;
import com.toplyh.android.scheduler.service.entity.remote.HttpsResult;
import com.toplyh.android.scheduler.service.entity.remote.Meeting;
import com.toplyh.android.scheduler.service.entity.remote.Member;
import com.toplyh.android.scheduler.service.entity.remote.MetAndMem;
import com.toplyh.android.scheduler.service.entity.remote.MsgCount;
import com.toplyh.android.scheduler.service.entity.remote.PJS;
import com.toplyh.android.scheduler.service.entity.remote.Project;
import com.toplyh.android.scheduler.service.entity.remote.ProjectAndMember;
import com.toplyh.android.scheduler.service.entity.remote.Projects;
import com.toplyh.android.scheduler.service.entity.remote.Sprint;
import com.toplyh.android.scheduler.service.entity.remote.UpdateCount;
import com.toplyh.android.scheduler.service.entity.state.Token;

import java.util.List;

import okhttp3.MultipartBody;
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

    public Observable<HttpsResult<List<Sprint>>> getSprintByProjectAndStatus(Integer projectId,String status){
        return mRetrofitService.getSprintByProjectAndStatus(projectId,status);
    }

    public Observable<HttpsResult<String>> newSprint(AddSprint addSprint){
        return mRetrofitService.newSprint(addSprint);
    }

    public Observable<HttpsResult<String>> changeSprintStatus(Integer sprintId,String status){
        return mRetrofitService.changeSprintStatus(sprintId,status);
    }

    public Observable<HttpsResult<List<MetAndMem>>> getMeetings(Integer projectId){
        return mRetrofitService.getMeetings(projectId);
    }

    public Observable<HttpsResult<String>> newMeeting(MetAndMem metAndMem){
        return mRetrofitService.newMeeting(metAndMem);
    }

    public Observable<HttpsResult<List<Member>>> getMembers(Integer projectId){
        return mRetrofitService.getMembers(projectId);
    }

    public Observable<HttpsResult<Integer>> getProgress(Integer projectId){
        return mRetrofitService.getProgress(projectId);
    }

    public Observable<String> upload(MultipartBody.Part file){
        return mRetrofitService.upload(file);
    }
}
