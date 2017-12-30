package com.toplyh.android.scheduler.service.presenter;

import android.content.Context;
import android.util.Log;

import com.toplyh.android.scheduler.service.ApiCallBack;
import com.toplyh.android.scheduler.service.entity.remote.HttpsResult;
import com.toplyh.android.scheduler.service.entity.remote.Project;
import com.toplyh.android.scheduler.service.entity.remote.Sprint;
import com.toplyh.android.scheduler.service.entity.state.StateTable;
import com.toplyh.android.scheduler.service.view.ProjectView;

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
                }else if (model.getState()==StateTable.SPRINT_NO_SPRINT){
                    mProjectView.toastMessage("没有sprint");
                }else if (model.getState()==StateTable.SPRINT_NO_MEMBER){
                    mProjectView.toastMessage("这个成员下没有sprint");
                }else if (model.getState()==StateTable.SPRINT_NO_PROJECT){
                    mProjectView.toastMessage("这个项目下没有sprint");
                }else if (model.getState()==StateTable.AUTHENTICATION_TOKEN_ERROR){
                    mProjectView.toastMessage("token已过期，请重新登录");
                }
                mProjectView.cancelSprintRefreshDialog();
            }

            @Override
            public void onFailure(String msg) {
                Log.e("dandy", "onFailure: "+msg );
                mProjectView.cancelSprintRefreshDialog();
            }

            @Override
            public void onFinish() {

            }
        };

        addSubscription(mRemoteManager.getSprintByProjectAndStatus(id,status.name()),subscriber);
    }

    public void destroy(){
        detachView();
    }

}
