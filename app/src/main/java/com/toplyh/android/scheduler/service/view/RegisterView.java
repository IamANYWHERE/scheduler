package com.toplyh.android.scheduler.service.view;

import com.toplyh.android.scheduler.service.entity.remote.HttpsResult;

/**
 * Created by 我 on 2017/11/23.
 */

public interface RegisterView extends View {
    String getUserName();
    String getPassword();
    String getRepeatPassword();
    void toLoginActivity();
}
