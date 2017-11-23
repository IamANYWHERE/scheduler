package com.toplyh.android.scheduler.service.view;

import com.toplyh.android.scheduler.service.entity.state.LoginState;

/**
 * Created by 我 on 2017/11/23.
 */

public interface LoginView extends View {
    void OnSuccess(LoginState state);
    void onError(String result);
}
