package com.toplyh.android.scheduler.service.presenter;

import android.content.Context;

import com.toplyh.android.scheduler.service.ApiCallBack;
import com.toplyh.android.scheduler.service.entity.remote.Count;
import com.toplyh.android.scheduler.service.entity.remote.HttpsResult;
import com.toplyh.android.scheduler.service.entity.state.StateTable;
import com.toplyh.android.scheduler.service.view.RegisterView;

/**
 * Created by Michael on 2017/12/25.
 */

public class RegisterPresenter extends BasePresenter<RegisterView> {


    private RegisterView mRegisterView;

    public RegisterPresenter(Context context,RegisterView registerView) {
        super(context);
        attachView(registerView);
        this.mRegisterView=registerView;
    }

    public void registerUser(){
        String name=mRegisterView.getUserName();
        String password=mRegisterView.getPassword();
        String repeatPassword=mRegisterView.getRepeatPassword();

        if(name==null||name.equals("")||password==null||password.equals("")||repeatPassword==null||repeatPassword.equals("")){
            mRegisterView.toastMessage("用户名或密码为空！");
            return;
        }

        if(!password.equals(repeatPassword)){
            mRegisterView.toastMessage("密码不一致！");
            return;
        }

        mRegisterView.showDialog();
        ApiCallBack<HttpsResult<String>> subscriber=new ApiCallBack<HttpsResult<String>>() {
            @Override
            public void onSuccess(HttpsResult<String> model) {
                if(model.getState()== StateTable.REGISTER_RIGHT) {
                    mRegisterView.cancelDialog();
                    mRegisterView.toLoginActivity();
                }else if(model.getState()==StateTable.REGISTER_ERROR){
                    mRegisterView.toastMessage("此账号已经注册！");
                }
            }

            @Override
            public void onFailure(String msg) {
                mRegisterView.cancelDialog();
                mRegisterView.toastMessage(msg);
            }

            @Override
            public void onFinish() {

            }
        };

        Count count=new Count();
        count.setName(name);
        count.setPassword(password);
        addSubscription(mRemoteManager.registerUser(count),subscriber);
    }


    public void destroy(){
        detachView();
    }
}
