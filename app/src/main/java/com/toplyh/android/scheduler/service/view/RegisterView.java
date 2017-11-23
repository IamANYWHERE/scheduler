package com.toplyh.android.scheduler.service.view;

import com.toplyh.android.scheduler.service.entity.state.RegisterState;

/**
 * Created by 我 on 2017/11/23.
 */

public interface RegisterView extends View {

    void OnSuccess(RegisterState registerState);
    void onError(String result);
}
