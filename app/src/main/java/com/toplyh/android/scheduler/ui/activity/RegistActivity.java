package com.toplyh.android.scheduler.ui.activity;

import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.view.LoginView;
//import butterknife.ButterKnife;

/**
 * Created by Michael on 2017/12/24.
 */

public class RegistActivity extends BaseActivity implements LoginView {
    @Override
    public void showDialog() {

    }

    @Override
    public void cancelDialog() {

    }

    @Override
    public void toastMessage(String msg) {

    }

    @Override
    public String getUseName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void toMainActivity() {

    }

    @Override
    public void showFailedError() {

    }

    @Override
    public void userOrPwdIsNull() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_regist);

    }

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
}
