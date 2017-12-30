package com.toplyh.android.scheduler.service.entity.remote;

/**
 * Created by 我 on 2017/12/29.
 */

public class UpdateCount {
    private String nickName;

    private String currentPassword;

    private String newPassword;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
