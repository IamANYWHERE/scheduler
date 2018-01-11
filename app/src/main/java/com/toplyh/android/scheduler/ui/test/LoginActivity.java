package com.toplyh.android.scheduler.ui.test;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;

public class LoginActivity extends BaseActivity implements LoginView {


    private LoginPresenter mLoginPresenter;
    private EditText mUserName;
    private EditText mPassword;
    private ProgressDialog mDialog;
    private Button mSingIn;
    private LinearLayout mLayout;
    private String mImagePath;



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

                //takePhoto();
            }
        });
    }

    /*private void takePhoto(){
        String SDState= Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)){
            String imageFilePath=getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
            String timestamp="/"+formatter.format(new Date())+".png";
            File imageFile=new File(imageFilePath,timestamp);
            mImagePath=imageFile.getAbsolutePath();
            Uri imageFileUri=Uri.fromFile(imageFile);
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageFileUri);
            startActivityForResult(intent,SELECT_IMAGE_RESULT_CODE);
        }else {
            Toast.makeText(this,"内存卡不存在！",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SELECT_IMAGE_RESULT_CODE&&requestCode==RESULT_OK){
            String imagePath="";
            if (data!=null&&data.getData()!=null){
                imagePath=getFilePathByFileUri(this,data.getData());
            }else {
                imagePath=mImagePath;
            }
            mUserName.setText(imagePath);
        }
    }*/


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
