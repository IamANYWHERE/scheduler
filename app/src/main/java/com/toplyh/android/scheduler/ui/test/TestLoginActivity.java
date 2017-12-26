package com.toplyh.android.scheduler.ui.test;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.RetrofitHelper;
import com.toplyh.android.scheduler.service.entity.remote.Count;
import com.toplyh.android.scheduler.service.entity.remote.HttpsResult;
import com.toplyh.android.scheduler.service.entity.state.Token;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TestLoginActivity extends AppCompatActivity {

    private TextView textName;
    private TextView textPassword;
    private Button buttonLogin;
    private TextView textResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_login);
        initView();
    }

    private void initView(){
        textName=(TextView)findViewById(R.id.test_name);
        textPassword=(TextView)findViewById(R.id.test_password);
        textResult=(TextView)findViewById(R.id.test_response_login);
        buttonLogin=(Button)findViewById(R.id.test_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Count count=new Count();
                count.setName(textName.getText().toString());
                count.setPassword(textPassword.getText().toString());
                RetrofitHelper.getInstance(TestLoginActivity.this)
                        .getServer().loginUser(count)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<HttpsResult<Token>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(HttpsResult<Token> tokenHttpsResult) {
                                if (tokenHttpsResult.getState()==100){
                                    textResult.setText("100:"+tokenHttpsResult.getData().getToken());
                                    Toast.makeText(TestLoginActivity.this,"onNext==100",Toast.LENGTH_SHORT);
                                }else {
                                    textResult.setText("!=100");
                                    Toast.makeText(TestLoginActivity.this,"onNext!=100",Toast.LENGTH_SHORT);
                                }
                            }
                        });
            }
        });
    }
}
