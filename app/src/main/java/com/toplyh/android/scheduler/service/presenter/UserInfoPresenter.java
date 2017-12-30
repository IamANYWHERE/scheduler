package com.toplyh.android.scheduler.service.presenter;

import android.content.Context;
import android.util.Log;

import com.toplyh.android.scheduler.service.ApiCallBack;
import com.toplyh.android.scheduler.service.entity.remote.HttpsResult;
import com.toplyh.android.scheduler.service.entity.remote.MsgCount;
import com.toplyh.android.scheduler.service.entity.remote.UpdateCount;
import com.toplyh.android.scheduler.service.entity.state.StateTable;
import com.toplyh.android.scheduler.service.view.UserInfoView;

/**
 * Created by 我 on 2017/12/29.
 */

public class UserInfoPresenter extends BasePresenter<UserInfoView> {

    private UserInfoView mUserInfoView;

    public UserInfoPresenter(Context context,UserInfoView userInfoView) {
        super(context);
        attachView(userInfoView);
        this.mUserInfoView=userInfoView;
    }


    public void getUserMsg(){
        mUserInfoView.showDialog();
        ApiCallBack<HttpsResult<MsgCount>> subscriber=new ApiCallBack<HttpsResult<MsgCount>>() {
            @Override
            public void onSuccess(HttpsResult<MsgCount> model) {
                if (model.getState()==StateTable.GET_MSG_RIGHT){

                    if (model.getData().getNickName()==null&&model.getData().getNickName().equals("")){
                        mUserInfoView.setNickName(model.getData().getName());
                    }else {
                        mUserInfoView.setNickName(model.getData().getNickName());
                    }
                }else if (model.getState()==StateTable.AUTHENTICATION_TOKEN_ERROR){
                    mUserInfoView.toastMessage("请重新登录");
                }
                mUserInfoView.cancelDialog();
            }

            @Override
            public void onFailure(String msg) {
                Log.e("dandy", "onFailure: "+msg );
                mUserInfoView.cancelDialog();
            }

            @Override
            public void onFinish() {

            }
        };
        addSubscription(mRemoteManager.getUserMsg(),subscriber);
    }

    public void updateUser(){
        String nickName=mUserInfoView.getNickName();
        String currentPassword=mUserInfoView.getCurrentPassword();
        String newPassword=mUserInfoView.getNewPassword();
        String newRepeatPassword=mUserInfoView.getNewRepeatPassword();
        final Boolean hasNewPassword;
        if (newPassword.equals("")&&newRepeatPassword.equals("")){
            hasNewPassword=false;
        }else {
            hasNewPassword=true;
        }

        if (nickName.equals("")){
            mUserInfoView.toastMessage("昵称为空！");
            return;
        }

        if (currentPassword.equals("")){
            mUserInfoView.toastMessage("密码为空！");
            return;
        }

        if (!newPassword.equals(newRepeatPassword)){
            mUserInfoView.toastMessage("新密码不一致！");
            return;
        }
        mUserInfoView.showDialog();
        ApiCallBack<HttpsResult<String>> subscriber=new ApiCallBack<HttpsResult<String>>() {
            @Override
            public void onSuccess(HttpsResult<String> model) {
                if (model.getState()==StateTable.UPDATE_USER_RIGHT){
                    mUserInfoView.toastMessage("成功");
                    if (hasNewPassword){
                        mUserInfoView.toLoginActivity();
                    }
                }else if (model.getState()==StateTable.UPDATE_USER_PASSWORD_ERROR){
                    mUserInfoView.toastMessage("失败，密码错误！");
                }else if (model.getState()==StateTable.AUTHENTICATION_TOKEN_ERROR){
                    mUserInfoView.toastMessage("请重新登录");
                }
                mUserInfoView.cancelDialog();
            }

            @Override
            public void onFailure(String msg) {
                Log.e("dandy", "onFailure: "+msg );
                mUserInfoView.cancelDialog();
            }

            @Override
            public void onFinish() {

            }
        };

        UpdateCount updateCount=new UpdateCount();
        updateCount.setNickName(nickName);
        updateCount.setCurrentPassword(currentPassword);
        updateCount.setNewPassword(newPassword);
        addSubscription(mRemoteManager.updateUser(updateCount),subscriber);
    }
}
