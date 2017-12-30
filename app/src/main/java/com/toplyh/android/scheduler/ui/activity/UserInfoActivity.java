package com.toplyh.android.scheduler.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;
import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.presenter.UserInfoPresenter;
import com.toplyh.android.scheduler.service.view.UserInfoView;

public class UserInfoActivity extends BaseActivity implements UserInfoView{


    private UserInfoPresenter mUserInfoPresenter;
    private EditText userNameEdit;
    private FoldingCell mFoldingCell;
    private Button mModiftSubmitButton;
    private EditText currentPwdEdit;
    private EditText newPwdEdit;
    private EditText repeatPwdEdit;
    private ProgressDialog mProgressDialog;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_user_info);
        userNameEdit=findView(R.id.user_info_name);
        mFoldingCell=findView(R.id.folding_cell);
        mModiftSubmitButton=findView(R.id.submitModify);
        currentPwdEdit=findView(R.id.user_info_current_pwd);
        newPwdEdit=findView(R.id.user_info_new_pwd);
        repeatPwdEdit=findView(R.id.user_info_repeat_pwd);
    }

    @Override
    protected void initData() {
        mUserInfoPresenter=new UserInfoPresenter(UserInfoActivity.this,UserInfoActivity.this);
        mFoldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFoldingCell.toggle(false);
            }
        });
        mUserInfoPresenter.getUserMsg();

        mModiftSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserInfoPresenter.updateUser();
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
    public void showDialog() {
        mProgressDialog=ProgressDialog.show(UserInfoActivity.this,"提升","更新。。。");
    }

    @Override
    public void cancelDialog() {
        mProgressDialog.cancel();
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(UserInfoActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getNickName() {
        return userNameEdit.getText().toString();
    }

    @Override
    public String getCurrentPassword() {
        return currentPwdEdit.getText().toString();
    }

    @Override
    public String getNewPassword() {
        return newPwdEdit.getText().toString();
    }

    @Override
    public String getNewRepeatPassword() {
        return repeatPwdEdit.getText().toString();
    }

    @Override
    public void toLoginActivity() {
        startActivity(new Intent(UserInfoActivity.this,MaterialLoginActivity.class));
        finish();
    }

    @Override
    public void setNickName(String name) {
        userNameEdit.setText(name);
    }
}
