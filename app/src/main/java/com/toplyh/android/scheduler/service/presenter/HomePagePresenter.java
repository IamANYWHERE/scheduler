package com.toplyh.android.scheduler.service.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.toplyh.android.scheduler.service.ApiCallBack;
import com.toplyh.android.scheduler.service.entity.remote.Count;
import com.toplyh.android.scheduler.service.entity.remote.HttpsResult;
import com.toplyh.android.scheduler.service.entity.remote.PJS;
import com.toplyh.android.scheduler.service.entity.remote.Project;
import com.toplyh.android.scheduler.service.entity.remote.ProjectAndMember;
import com.toplyh.android.scheduler.service.entity.remote.Projects;
import com.toplyh.android.scheduler.service.entity.state.StateTable;
import com.toplyh.android.scheduler.service.view.HomePageView;

import java.util.Date;
import java.util.List;

/**
 * Created by 我 on 2017/12/28.
 */

public class HomePagePresenter extends BasePresenter<HomePageView> {

    private HomePageView mHomePageView;
    public HomePagePresenter(Context context,HomePageView homePageView) {
        super(context);
        attachView(homePageView);
        this.mHomePageView=homePageView;
    }


    public void getProjects(){
        Toast.makeText(mContext, "Start to get Projects", Toast.LENGTH_SHORT).show();
        mHomePageView.showDialog();
        ApiCallBack<HttpsResult<Projects>> subscriber=new ApiCallBack<HttpsResult<Projects>>() {
            @Override
            public void onSuccess(HttpsResult<Projects> model) {
                if (model.getState()== StateTable.PROJECT_SHOW_RIGHT){
                    mHomePageView.initRecyclerView(model.getData());
                }else if (model.getState()==StateTable.AUTHENTICATION_TOKEN_ERROR){
                    mHomePageView.toastMessage("请重新登录");
                }
                mHomePageView.cancelDialog();
            }

            @Override
            public void onFailure(String msg) {
                Log.e("dandy", "onFailure: "+msg );
                mHomePageView.cancelDialog();
            }

            @Override
            public void onFinish() {

            }
        };
        addSubscription(mRemoteManager.getProjects(),subscriber);
    }

    public void newProject(){
        ProjectAndMember projectAndMember=mHomePageView.getProjectAndMember();

        ApiCallBack<HttpsResult<String>> subscriber=new ApiCallBack<HttpsResult<String>>() {
            @Override
            public void onSuccess(HttpsResult<String> model) {
                if (model.getState()==StateTable.PROJECT_ADD_RIGHT){
                    getProjects();
                }else if (model.getState()==StateTable.AUTHENTICATION_TOKEN_ERROR){
                    mHomePageView.toastMessage("请重新登录");
                }
            }

            @Override
            public void onFailure(String msg) {
                Log.e("dandy", "onFailure: "+msg);
            }

            @Override
            public void onFinish() {

            }
        };

        Log.e("george","projectName="+projectAndMember.getProjectName()+"  ddl="+projectAndMember.getDdl());
        addSubscription(mRemoteManager.newProject(projectAndMember),subscriber);
    }




    public void destroy(){
        detachView();
    }
}
