package com.toplyh.android.scheduler.ui.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.entity.User;
import com.toplyh.android.scheduler.service.entity.state.LoginState;
import com.toplyh.android.scheduler.service.presenter.LoginPresenter;
import com.toplyh.android.scheduler.service.view.LoginView;

public class LoginActivity extends AppCompatActivity {

    private EditText mEditTextName;
    private EditText mEditTextPassword;
    private Button mButtonLogin;
    private TextView mTextView;

    private LoginPresenter mLoginPresenter=new LoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditTextName=(EditText)findViewById(R.id.edit_name);
        mEditTextPassword=(EditText)findViewById(R.id.edit_password);
        mButtonLogin=(Button)findViewById(R.id.btn_login);
        mTextView=(TextView)findViewById(R.id.text_response_login);

        mLoginPresenter.onCreate();
        mLoginPresenter.attachView(mLoginView);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user=new User();
                user.setName(mEditTextName.getText()+"");
                user.setPassword(mEditTextPassword.getText()+"");
                mLoginPresenter.LoginUser(user);
            }
        });

    }

    private LoginView mLoginView=new LoginView() {
        @Override
        public void OnSuccess(LoginState state) {
            mTextView.setText("state:"+state.getState()+"\n"+
            "token:"+state.getToken());
        }

        @Override
        public void onError(String result) {
            mTextView.setText(result);
        }
    };
}
