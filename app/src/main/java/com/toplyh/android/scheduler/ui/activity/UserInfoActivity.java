package com.toplyh.android.scheduler.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;
import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.presenter.UserInfoPresenter;
import com.toplyh.android.scheduler.service.utils.ImageHelper;
import com.toplyh.android.scheduler.service.view.UserInfoView;
import com.toplyh.android.scheduler.ui.test.NettyActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class UserInfoActivity extends BaseActivity implements UserInfoView{

    private static final int USER_IMAGE=0;

    private UserInfoPresenter mUserInfoPresenter;
    private EditText userNameEdit;
    private FoldingCell mFoldingCell;
    private Button mModiftSubmitButton;
    private EditText currentPwdEdit;
    private EditText newPwdEdit;
    private EditText repeatPwdEdit;
    private ImageView userImage;
    private ProgressDialog mProgressDialog;
    private String mImagePath;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_user_info);
        userNameEdit=findView(R.id.user_info_name);
        mFoldingCell=findView(R.id.folding_cell);
        mModiftSubmitButton=findView(R.id.submitModify);
        currentPwdEdit=findView(R.id.user_info_current_pwd);
        newPwdEdit=findView(R.id.user_info_new_pwd);
        repeatPwdEdit=findView(R.id.user_info_repeat_pwd);
        userImage=findView(R.id.user_image);
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

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
    }
    private void pickPhoto(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,USER_IMAGE);
    }

    private void takePhoto(){
        String SDState= Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)){
            String imageFilePath=getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
            String timestamp="/"+formatter.format(new Date())+".png";
            File imageFile=new File(imageFilePath,timestamp);
            mImagePath=imageFile.getAbsolutePath();
            Uri imageFileUri= Uri.fromFile(imageFile);
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageFileUri);
            startActivityForResult(intent,USER_IMAGE);
        }else {
            Toast.makeText(this,"内存卡不存在！",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==USER_IMAGE&&resultCode==RESULT_OK){
            String imagePath="";
            if (data!=null&&data.getData()!=null){
                imagePath= ImageHelper.getFilePathByFileUri(this,data.getData());

            }else {
                imagePath=mImagePath;
            }
            mImagePath=imagePath;
        }
    }

    public void saveBitmapFile(Bitmap bitmap,String path){
        File file=new File(path);
        try{
            BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
            bos.flush();
            bos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void uploadFile(){
        mUserInfoPresenter.upload();
    }

    private void compressImage( ){
        String path=UserInfoActivity.this.getFilesDir().getAbsolutePath();
        path=path+File.separator+ImageHelper.DIRECTORY_NAME+File.separator+new Date().toString()+".png";
        ImageHelper.compressImage(mImagePath,path,30);
        mImagePath=path;
    }

    @Override
    protected void addToolbar() {
        Toolbar toolbar=findView(R.id.user_info_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public String getFilePath() {
        return mImagePath;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserInfoPresenter.destroy();
    }
}
