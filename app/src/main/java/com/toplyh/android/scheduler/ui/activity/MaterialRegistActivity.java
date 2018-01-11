package com.toplyh.android.scheduler.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.presenter.RegisterPresenter;
import com.toplyh.android.scheduler.service.view.LoginView;
import com.toplyh.android.scheduler.service.view.RegisterView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MaterialRegistActivity extends BaseActivity implements RegisterView {

    private TextView mRegistUsername;
    private TextView mRegistPassword;
    private TextView mRegistRepeatPassword;
    private Button mButtonRegist;
    private FloatingActionButton fabLogin;
    private RelativeLayout mLayout;
    private CardView cvAdd;
    private ProgressDialog mProgressDialog;
    private RegisterPresenter mRegisterPresenter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_material_regist);
        mRegistUsername=findView(R.id.regist_username);
        mRegistPassword=findView(R.id.regist_password);
        mRegistRepeatPassword=findView(R.id.regist_repeatpassword);
        mButtonRegist=findView(R.id.bt_regist);
        fabLogin=findView(R.id.fab_to_login);
        mLayout=findView(R.id.material_regist_layout);
        cvAdd=findView(R.id.cv_add);
        ButterKnife.inject(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        fabLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });

        mButtonRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegisterPresenter.registerUser();
            }
        });

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }

    @Override
    protected void initData() {
        super.initData();
        mRegisterPresenter=new RegisterPresenter(MaterialRegistActivity.this,MaterialRegistActivity.this);
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

    @Override
    public void showDialog() {
        mProgressDialog=ProgressDialog.show(MaterialRegistActivity.this,"提示","正在注册...");
    }

    @Override
    public void cancelDialog() {
        mProgressDialog.cancel();
    }

    @Override
    public void toastMessage(String msg) {
        Snackbar.make(mLayout,msg,Snackbar.LENGTH_LONG)
                .setAction("ok",null).show();
    }

    @Override
    public String getUserName() {
        return mRegistUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return mRegistPassword.getText().toString();
    }

    @Override
    public String getRepeatPassword() {
        return mRegistRepeatPassword.getText().toString();
    }

    @Override
    public void toLoginActivity() {
        animateRevealClose();
    }



    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2,0, fabLogin.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }



    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd,cvAdd.getWidth()/2,0, cvAdd.getHeight(), fabLogin.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fabLogin .setImageResource(R.drawable.plus);
                MaterialRegistActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRegisterPresenter.destroy();
    }
}
