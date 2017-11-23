package com.toplyh.android.scheduler.ui.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.entity.User;
import com.toplyh.android.scheduler.service.entity.state.RegisterState;
import com.toplyh.android.scheduler.service.presenter.RegisterPresenter;
import com.toplyh.android.scheduler.service.view.RegisterView;

public class RegistActivity extends AppCompatActivity {

    private static final String TAG="toplyh";

    private EditText mEditTextName;
    private EditText mEditTextPassword;
    private Button mButton;
    private TextView mTextView;

    private RegisterPresenter mRegisterPresenter =new RegisterPresenter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        mEditTextName=(EditText)findViewById(R.id.text_name);
        mEditTextPassword=(EditText)findViewById(R.id.text_password);
        mButton=(Button)findViewById(R.id.btn_register);
        mTextView=(TextView)findViewById(R.id.text_response);

        mRegisterPresenter.onCreate();
        mRegisterPresenter.attachView(mRegisterView);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user=new User();
                user.setName(mEditTextName.getText().toString());
                user.setPassword(mEditTextPassword.getText().toString());
                Log.i(TAG, "onClick: "+mEditTextName.getText()+mEditTextPassword.getText());
                mRegisterPresenter.registerUser(user);
            }
        });

    }

    private RegisterView mRegisterView =new RegisterView() {
        @Override
        public void OnSuccess(RegisterState state) {
            mTextView.setText(state.getState()+"");
        }

        @Override
        public void onError(String result) {
            mTextView.setText(result);
        }
    };
}
