package com.toplyh.android.scheduler.ui.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.entity.Note;
import com.toplyh.android.scheduler.service.presenter.LoginPresenter;
import com.toplyh.android.scheduler.service.view.LoginView;
import com.toplyh.android.scheduler.ui.activity.BaseActivity;

public class LoginActivity extends BaseActivity implements LoginView {

    private LoginPresenter mLoginPresenter;
    private EditText mUserName;
    private EditText mPassword;
    private ProgressDialog mDialog;
    private Button mSingIn;
    private LinearLayout mLayout;



    @Override
    public void showDialog() {
        mDialog=ProgressDialog.show(LoginActivity.this,"提示","正在登录...");
    }

    @Override
    public void cancelDialog() {
        mDialog.cancel();
    }

    @Override
    public void toastMessage(String msg) {
        Snackbar.make(mLayout,msg,Snackbar.LENGTH_LONG)
                .setAction("Action",null).show();
    }

    @Override
    public String getUseName() {
        return mUserName.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPassword.getText().toString();
    }

    @Override
    public void toMainActivity() {
        startActivity(new Intent(LoginActivity.this, NoteActivity.class));
    }

    @Override
    public void showFailedError() {

    }

    @Override
    public void userOrPwdIsNull() {
        Toast.makeText(LoginActivity.this,"用户或者密码不能为空",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);

        mLayout=findView(R.id.login_layout);
        mUserName=findView(R.id.edit_name);
        mPassword=findView(R.id.edit_password);
        mSingIn=findView(R.id.btn_login);

        addToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }

    @Override
    protected void initData() {
        mLoginPresenter=new LoginPresenter(this,this);
        mSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.loginUser();
            }
        });
    }

    @Override
    protected void addToolbar() {

    }

    @Override
    protected String[] getNeedPermissions() {
        return new String[0];
    }

    @Override
    protected void permissionGrantedSuccess() {

    }

    @Override
    protected void permissionGrantedFail() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.destroy();
    }
}
