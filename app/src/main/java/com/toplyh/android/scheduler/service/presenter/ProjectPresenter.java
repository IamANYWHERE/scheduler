package com.toplyh.android.scheduler.service.presenter;

import android.content.Context;
import android.util.Log;

import com.toplyh.android.scheduler.service.ApiCallBack;
import com.toplyh.android.scheduler.service.entity.remote.AddSprint;
import com.toplyh.android.scheduler.service.entity.remote.HttpsResult;
import com.toplyh.android.scheduler.service.entity.remote.Meeting;
import com.toplyh.android.scheduler.service.entity.remote.Member;
import com.toplyh.android.scheduler.service.entity.remote.MetAndMem;
import com.toplyh.android.scheduler.service.entity.remote.Sprint;
import com.toplyh.android.scheduler.service.entity.state.StateTable;
import com.toplyh.android.scheduler.service.view.ProjectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 我 on 2017/12/30.
 */

public class ProjectPresenter extends BasePresenter<ProjectView> {

    private ProjectView mProjectView;

    public ProjectPresenter(Context context,ProjectView projectView) {
        super(context);
        attachView(projectView);
        mProjectView=projectView;
    }

    public void getSprintByProjectAndStatus(){
        Integer id=mProjectView.getProjectId();
        Sprint.SprintStatus status=mProjectView.getStatus();
        mProjectView.showSprintRefreshDialog();
        ApiCallBack<HttpsResult<List<Sprint>>> subscriber=new ApiCallBack<HttpsResult<List<Sprint>>>() {
            @Override
            public void onSuccess(HttpsResult<List<Sprint>> model) {
                if (model.getState()== StateTable.SPRINT_SHOW_RIGHT){
                    mProjectView.initSprintView(model.getData());
                    mProjectView.toastMessage("获取sprint成功");
                }else if (model.getState()==StateTable.SPRINT_NO_STATUS){
                    mProjectView.initSprintView(new ArrayList<Sprint>());
                  mProjectView.toastMessage("这个状态下没有sprint");
                } else if (model.getState()==StateTable.SPRINT_NO_SPRINT){
                    mProjectView.initSprintView(new ArrayList<Sprint>());
                    mProjectView.toastMessage("没有sprint");
                }else if (model.getState()==StateTable.SPRINT_NO_MEMBER){
                    mProjectView.initSprintView(new ArrayList<Sprint>());
                    mProjectView.toastMessage("这个成员下没有sprint");
                }else if (model.getState()==StateTable.SPRINT_NO_PROJECT){
                    mProjectView.initSprintView(new ArrayList<Sprint>());
                    mProjectView.toastMessage("这个项目下没有sprint");
                }else if (model.getState()==StateTable.AUTHENTICATION_TOKEN_ERROR){
                    mProjectView.toastMessage("token已过期，请重新登录");
                }
            }

            @Override
            public void onFailure(String msg) {
                Log.e("dandy", "onFailure: "+msg );
                mProjectView.toastMessage(msg);
            }

            @Override
            public void onFinish() {
                mProjectView.cancelSprintRefreshDialog();
            }
        };

        addSubscription(mRemoteManager.getSprintByProjectAndStatus(id,status.name()),subscriber);
    }

    public void newSprint(){
        AddSprint addSprint=new AddSprint();
        addSprint.setProjectId(mProjectView.getProjectId());
        addSprint.setMemberName(mProjectView.getSprintMember());
        addSprint.setSprint(new AddSprint.SprintBean());
        addSprint.getSprint().setName(mProjectView.getSprintName());
        addSprint.getSprint().setContent(mProjectView.getSprintContent());
        addSprint.getSprint().setDdl(mProjectView.getSprintDDL());
        addSprint.getSprint().setStatus(mProjectView.getNewSprintStatus().name());
        addSprint.getSprint().setWorkTime(mProjectView.getSprintWorkTime());

        ApiCallBack<HttpsResult<String>> subscriber=new ApiCallBack<HttpsResult<String>>() {
            @Override
            public void onSuccess(HttpsResult<String> model) {
                if (model.getState()==StateTable.SPRINT_ADD_RIGHT){
                    mProjectView.toastMessage("成功");
                }else if (model.getState()==StateTable.SPRINT_NO_MEMBER){
                    mProjectView.toastMessage("没有这个成员");
                }else if (model.getState()==StateTable.SPRINT_NO_PROJECT){
                    mProjectView.toastMessage("没有这个项目");
                }else if (model.getState()==StateTable.AUTHENTICATION_TOKEN_ERROR){
                    mProjectView.toastMessage("token过期，请重新登录");
                }
            }

            @Override
            public void onFailure(String msg) {
                Log.e("dandy", "onFailure: "+msg );
                mProjectView.toastMessage(msg);
            }

            @Override
            public void onFinish() {

            }
        };

        addSubscription(mRemoteManager.newSprint(addSprint),subscriber);
    }

    public void changeSprintStatus(){
        Integer sprintId=mProjectView.getChangeSprintId();
        Sprint.SprintStatus status=mProjectView.getChangeSprintStatus();

        ApiCallBack<HttpsResult<String>> subscriber=new ApiCallBack<HttpsResult<String>>() {
            @Override
            public void onSuccess(HttpsResult<String> model) {
                if (model.getState()==StateTable.SPRINT_CHANGE_STATUS_RIGHT){
                    mProjectView.toastMessage("sprint状态更改成功");
                }else if (model.getState()==StateTable.SPRINT_NO_SPRINT){
                    mProjectView.toastMessage("没有这个sprint");
                }else if (model.getState()==StateTable.AUTHENTICATION_TOKEN_ERROR){
                    mProjectView.toastMessage("token已过期，请重新登录");
                }
            }

            @Override
            public void onFailure(String msg) {
                Log.e("dandy", "onFailure: "+msg );
                mProjectView.toastMessage(msg);
            }

            @Override
            public void onFinish() {

            }
        };

        addSubscription(mRemoteManager.changeSprintStatus(sprintId,status.name()),subscriber);
    }


    public void getMeetings(){
        Integer projectId=mProjectView.getProjectId();
        mProjectView.showMeetingRefreshDialog();
        ApiCallBack<HttpsResult<List<MetAndMem>>> subscriber=new ApiCallBack<HttpsResult<List<MetAndMem>>>() {
            @Override
            public void onSuccess(HttpsResult<List<MetAndMem>> model) {
                if (model.getState()==StateTable.MEETING_SHOW_RIGHT){
                    mProjectView.initMeetingView(model.getData());
                    mProjectView.toastMessage("找到会议了");
                }else if (model.getState()==StateTable.MEETING_NO_MEETING){
                    mProjectView.toastMessage("没有会议");
                }else if (model.getState()==StateTable.MEETING_NO_PROJECT){
                    mProjectView.toastMessage("没有这个项目");
                }else if (model.getState()==StateTable.AUTHENTICATION_TOKEN_ERROR){
                    mProjectView.toastMessage("token过期，请重新登录");
                }
            }

            @Override
            public void onFailure(String msg) {
                Log.e("dandy", "onFailure: "+msg );
                mProjectView.toastMessage(msg);
            }

            @Override
            public void onFinish() {
                mProjectView.cancelMeetingRefreshDialog();
            }
        };
        addSubscription(mRemoteManager.getMeetings(projectId),subscriber);
    }

    public void newMeeting(){
        String name=mProjectView.getMeetingName();
        List<String> members=mProjectView.getMeetingMembers();
        long date=mProjectView.getMeetingDate();
        Integer projectId=mProjectView.getProjectId();

        ApiCallBack<HttpsResult<String>> subscriber=new ApiCallBack<HttpsResult<String>>() {
            @Override
            public void onSuccess(HttpsResult<String> model) {
                if (model.getState()==StateTable.MEETING_AND_MEMBER_ADD_RIGHT){
                    getMeetings();
                    mProjectView.toastMessage("添加成功");
                }else if (model.getState()==StateTable.MEETING_NO_PROJECT){
                    mProjectView.toastMessage("没有这个项目");
                }else if (model.getState()==StateTable.AUTHENTICATION_TOKEN_ERROR){
                    mProjectView.toastMessage("token过期，请重新登录");
                }
            }

            @Override
            public void onFailure(String msg) {
                Log.e("dandy", "onFailure: "+msg );
                mProjectView.toastMessage(msg);
            }

            @Override
            public void onFinish() {

            }
        };

        MetAndMem metAndMem=new MetAndMem();
        metAndMem.setName(name);
        metAndMem.setMembers(members);
        metAndMem.setDate(date);
        metAndMem.setProjectId(projectId);
        addSubscription(mRemoteManager.newMeeting(metAndMem),subscriber);
    }

    public void getMembers(){
        Integer projectId=mProjectView.getProjectId();

        mProjectView.showMemberRefreshDialog();
        ApiCallBack<HttpsResult<List<Member>>> subscriber=new ApiCallBack<HttpsResult<List<Member>>>() {
            @Override
            public void onSuccess(HttpsResult<List<Member>> model) {
                if (model.getState()==StateTable.MEMBER_SHOW_RIGHT){
                    if (model.getData()!=null){
                        mProjectView.initMemberView(model.getData());
                    }
                    mProjectView.toastMessage("找到成员了");
                }else if (model.getState()==StateTable.MEMBER_NO_MEMBER){
                    mProjectView.toastMessage("没有成员");
                }else if (model.getState()==StateTable.MEMBER_NO_PROJECT){
                    mProjectView.toastMessage("没有这个项目");
                }else if (model.getState()==StateTable.AUTHENTICATION_TOKEN_ERROR){
                    mProjectView.toastMessage("token已过期，请重新登录");
                }
            }

            @Override
            public void onFailure(String msg) {
                Log.e("dandy", "onFailure: "+msg );
                mProjectView.toastMessage(msg);
            }

            @Override
            public void onFinish() {
                mProjectView.cancelMemberRefreshDialog();
            }
        };

        addSubscription(mRemoteManager.getMembers(projectId),subscriber);
    }

    public void getProgress(){
        Integer projectId=mProjectView.getProjectId();

        mProjectView.showProgressRefreshDialog();
        ApiCallBack<HttpsResult<Integer>> subscriber=new ApiCallBack<HttpsResult<Integer>>() {
            @Override
            public void onSuccess(HttpsResult<Integer> model) {
                if (model.getState()==StateTable.PROJECT_SHOW_RIGHT){
                    mProjectView.initProgressView(model.getData());
                    Log.e("george","progress="+model.getData());
                    mProjectView.toastMessage("进度获取正常");
                }else if (model.getState()==StateTable.PROJECT_NO_PROJECT){
                    mProjectView.toastMessage("没有这个项目");
                }else if (model.getState()==StateTable.AUTHENTICATION_TOKEN_ERROR){
                    mProjectView.toastMessage("token已过期，请从新登录");
                }
            }

            @Override
            public void onFailure(String msg) {
                Log.e("dandy", "onFailure: "+msg );
                mProjectView.toastMessage(msg);
            }

            @Override
            public void onFinish() {
                mProjectView.cancelProgressRefreshDialog();
            }
        };

        addSubscription(mRemoteManager.getProgress(projectId),subscriber);
    }

    public void destroy(){
        detachView();
    }

}
