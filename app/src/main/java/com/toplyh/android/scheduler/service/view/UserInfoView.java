package com.toplyh.android.scheduler.service.view;

/**
 * Created by 我 on 2017/12/29.
 */

public interface UserInfoView extends View{
    String getNickName();
    String getCurrentPassword();
    String getNewPassword();
    String getNewRepeatPassword();
    void toLoginActivity();
    void setNickName(String name);
    String getFilePath();
}
