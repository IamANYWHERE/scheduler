package com.toplyh.android.scheduler.service.view;

import com.toplyh.android.scheduler.service.entity.remote.HttpsResult;
import com.toplyh.android.scheduler.service.entity.state.Token;

/**
 * Created by 我 on 2017/11/23.
 */

public interface LoginView extends View {
    String getUseName();
    String getPassword();
    void toMainActivity();
    void showFailedError();
    void userOrPwdIsNull();
}
