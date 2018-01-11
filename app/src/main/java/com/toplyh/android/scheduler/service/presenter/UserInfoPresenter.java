package com.toplyh.android.scheduler.service.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.toplyh.android.scheduler.service.ApiCallBack;
import com.toplyh.android.scheduler.service.entity.remote.HttpsResult;
import com.toplyh.android.scheduler.service.entity.remote.MsgCount;
import com.toplyh.android.scheduler.service.entity.remote.UpdateCount;
import com.toplyh.android.scheduler.service.entity.state.StateTable;
import com.toplyh.android.scheduler.service.view.UserInfoView;
import com.toplyh.android.scheduler.ui.test.LoginActivity;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
            }

            @Override
            public void onFailure(String msg) {
                Log.e("dandy", "onFailure: "+msg );
            }

            @Override
            public void onFinish() {
                mUserInfoView.cancelDialog();
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
            }

            @Override
            public void onFailure(String msg) {
                Log.e("dandy", "onFailure: "+msg );
            }

            @Override
            public void onFinish() {
                mUserInfoView.cancelDialog();
            }
        };

        UpdateCount updateCount=new UpdateCount();
        updateCount.setNickName(nickName);
        updateCount.setCurrentPassword(currentPassword);
        updateCount.setNewPassword(newPassword);
        addSubscription(mRemoteManager.updateUser(updateCount),subscriber);
    }

    public void upload(){
        String filePath=mUserInfoView.getFilePath();
        File file=new File(filePath);
        if (!file.exists()){
            mUserInfoView.toastMessage("文件不存在");
            return;
        }

        RequestBody requesFile=RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body=MultipartBody.Part.createFormData("file",file.getName(),requesFile);
        String descriptionString = "hello,这是文件描述";
        RequestBody description=RequestBody.create(MediaType.parse("multipart/form-data"),descriptionString);

        ApiCallBack<String> subscriber=new ApiCallBack<String>() {
            @Override
            public void onSuccess(String model) {
                mUserInfoView.toastMessage(model);
            }

            @Override
            public void onFailure(String msg) {
                mUserInfoView.toastMessage(msg);
            }

            @Override
            public void onFinish() {

            }
        };

        addSubscription(mRemoteManager.upload(body),subscriber);
    }

    public void destroy(){
        detachView();
    }
}
