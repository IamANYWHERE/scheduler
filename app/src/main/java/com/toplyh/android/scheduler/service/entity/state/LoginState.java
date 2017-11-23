package com.toplyh.android.scheduler.service.entity.state;

/**
 * Created by 我 on 2017/11/23.
 */

public class LoginState {

    private String token;

    private int state;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
