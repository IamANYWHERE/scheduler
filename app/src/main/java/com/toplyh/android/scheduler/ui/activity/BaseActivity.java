package com.toplyh.android.scheduler.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.Toast;

import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    protected Context mContext;
    private static final int REQUEST_CODE_PERMISSION=2020;//权限请求码
    private boolean isNeedCheckPermission=true;//判断是否需要检测，防止无限弹框申请权限

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    protected void initData(){

    }

    protected abstract void initView();

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedCheckPermission){
            checkAllPermission();
        }
    }
    /**
     * 检查全部的权限，无权限则申请相关权限
     */
    protected void checkAllPermission(){
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            List<String> needRequestPermissionList=getDeniedPermissions(getNeedPermissions());
            if (null!=needRequestPermissionList&&needRequestPermissionList.size()>0){
                ActivityCompat.requestPermissions(this,needRequestPermissionList.toArray(
                        new String[needRequestPermissionList.size()]),REQUEST_CODE_PERMISSION );
            }
        }
    }
    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     */
    private List<String> getDeniedPermissions(String[] permissions){
        List<String> needRequestPermissionList=new ArrayList<>();
        for (String permission : permissions){
            if (ContextCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED||
                ActivityCompat.shouldShowRequestPermissionRationale(this,permission)){
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }

    protected abstract void addToolbar();
    /*public void addToolbar(){
        mToolbar=findView(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }*/
    /**
     * 弹出Toast
     *
     * @param text
     */
    public void showToast(String text){
        Toast toast=Toast.makeText(this,text,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    /**
     * 解决绑定view时类型转换
     * @param id
     * @param <E>
     * @return
     */
    public final <E extends android.view.View> E findView(int id){
        try{
            return (E)findViewById(id);
        }catch (ClassCastException e){
            throw  e;
        }
    }
    /**
     * 授权之后回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_CODE_PERMISSION){
            if (!verifyPermissions(grantResults)){
                permissionGrantedFail();
                showTipDialog();
                isNeedCheckPermission=false;
            }else permissionGrantedSuccess();
        }
    }
    /**
     * 检测所有的权限是否都已授权
     *
     * @param grantResults
     * @return
     */
    private boolean verifyPermissions(int[] grantResults){
        for (int grantResult:grantResults){
            if (grantResult!=PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }
    /**
     * 显示提示对话框
     */
    protected void showTipDialog(){
        new AlertDialog.Builder(this).setTitle("信息").setMessage("当前应用缺少"+getDialogTipsPart()
        +"权限，该功能暂时无法使用。如若需要，请点击【确定】按钮前往设置中心进行权限授权。")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        }).show();
    }
    /**
     * 启动当前应用设置页面
     */
    private void startAppSettings(){
        Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:"+getPackageName()));
        startActivity(intent);
    }
    /**
     * 获取需要进行检测的权限数组
     */
    protected abstract String[] getNeedPermissions();

    /**
     * 获取弹框提示部分内容
     *
     * @return
     */
    protected String getDialogTipsPart(){
        return "必要";
    }
    /**
     * 权限授权成功
     */
    protected abstract void permissionGrantedSuccess();

    /**
     * 权限授权失败
     */
    protected abstract void permissionGrantedFail();
}
