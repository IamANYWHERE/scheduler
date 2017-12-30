package com.toplyh.android.scheduler.ui.activity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.presenter.LoginPresenter;
import com.toplyh.android.scheduler.service.view.HomePageView;
import com.toplyh.android.scheduler.service.view.LoginView;
import com.toplyh.android.scheduler.ui.test.LoginActivity;
import com.toplyh.android.scheduler.ui.test.ReactiveNoteActivity;

public class MaterialLoginActivity extends BaseActivity implements LoginView {


    private LoginPresenter mLoginPresenter;
    private TextView mTextUserName;
    private TextView mTextPassword;
    private Button mButtonLogin;
    private FloatingActionButton fabRegister;
    private RelativeLayout mLayout;
    private ProgressDialog mProgressDialog;

    @Override
    public void showDialog() {
        mProgressDialog=ProgressDialog.show(MaterialLoginActivity.this,"提示","正在登陆...");
    }

    @Override
    public void cancelDialog() {
        mProgressDialog.cancel();
    }

    @Override
    public void toastMessage(String msg) {
        Snackbar.make(mLayout,msg,Snackbar.LENGTH_LONG)
                .setAction("ok",null).show();
    }

    @Override
    public String getUseName() {
        return mTextUserName.getText().toString();
    }

    @Override
    public String getPassword() {
        return mTextPassword.getText().toString();
    }

    @Override
    public void toMainActivity() {
        startActivity(new Intent(MaterialLoginActivity.this, HomePageActivity.class));
        finish();
    }

    @Override
    public void showFailedError() {

    }

    @Override
    public void userOrPwdIsNull() {
        Snackbar.make(mLayout,"账号密码不能为空",Snackbar.LENGTH_LONG)
                .setAction("ok",null).show();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_material_login);
        mTextUserName=findView(R.id.et_username);
        mTextPassword=findView(R.id.et_password);
        mButtonLogin=findView(R.id.bt_go);
        mLayout=findView(R.id.material_login_layout);
        fabRegister=findView(R.id.fab);

    }

    @Override
    protected void initData() {
        super.initData();
        mLoginPresenter=new LoginPresenter(MaterialLoginActivity.this,MaterialLoginActivity.this);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.loginUser();
            }
        });
        fabRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MaterialLoginActivity.this, MaterialRegistActivity.class));


                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(MaterialLoginActivity.this, fabRegister, fabRegister.getTransitionName());
                    startActivity(new Intent(MaterialLoginActivity.this, MaterialRegistActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(MaterialLoginActivity.this, MaterialRegistActivity.class));
                }

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
}
