package com.toplyh.android.scheduler.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.RetrofitHelper;
import com.toplyh.android.scheduler.service.entity.remote.HttpsResult;
import com.toplyh.android.scheduler.service.entity.remote.PJS;
import com.toplyh.android.scheduler.service.entity.remote.Project;
import com.toplyh.android.scheduler.service.entity.remote.ProjectAndMember;
import com.toplyh.android.scheduler.service.entity.remote.Projects;
import com.toplyh.android.scheduler.service.presenter.HomePagePresenter;
import com.toplyh.android.scheduler.service.view.HomePageView;
import com.toplyh.android.scheduler.ui.adapter.HomePageAdapter;
import com.toplyh.android.scheduler.ui.fragment.AddProjectDialogFragment;
import com.yalantis.jellytoolbar.listener.JellyListener;
import com.yalantis.jellytoolbar.widget.JellyToolbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class HomePageActivity extends BaseActivity implements HomePageView,AddProjectDialogFragment.OnAddFragmentInteractionListener{

    private HomePagePresenter mHomePagePresenter;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FloatingActionButton mFloatingActionButton;

    private RecyclerView mRecyclerView;
    private HomePageAdapter mAdapter;
    private List<Project> mProjects;

    private TextView mTagsText;
    private ImageView mToolBarImageView;
    private TextView mToolBarTitleTextView;

    private JellyToolbar mToolbar;
    private AppCompatEditText mEditText;
    private AddProjectDialogFragment mAddProjectDialogFragment;
    private ProjectAndMember mProjectAndMember;

    private SwipeRefreshLayout mRefreshLayout;

    private static final String ADDPROJECTDIALOGFRAGMENT = "addprojectdialogfragment";


    @Override
    public void showDialog() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void cancelDialog() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(HomePageActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toProjectPage() {

    }

    @Override
    public void toPersonalInfoPage() {

    }

    @Override
    public void toHistoryPage() {

    }

    @Override
    public void toAbout() {

    }

    @Override
    public void toSet() {

    }

    @Override
    public void toLoginPage() {

    }

    @Override
    public void initRecyclerView(Projects projects) {
        mProjects=new ArrayList<>();
        List<Project> selfProjects=new ArrayList<>();
        for (int i=0;i<projects.getSelfProjects().size();i++){
            Integer id=projects.getSelfProjects().get(i).getId();
            String name=projects.getSelfProjects().get(i).getProjectName();
            Date date=new Date(projects.getSelfProjects().get(i).getDdl());
            Integer progress=projects.getSelfProjects().get(i).getProgress();
            Project project=new Project(id,name,date,progress);
            selfProjects.add(project);
        }
        List<Project> otherProjects=new ArrayList<>();
        for (int i=0;i<projects.getOtherProjects().size();i++){
            Integer id=projects.getOtherProjects().get(i).getId();
            String name=projects.getOtherProjects().get(i).getProjectName();
            Date date=new Date(projects.getOtherProjects().get(i).getDdl());
            Integer progress=projects.getOtherProjects().get(i).getProgress();
            Project project=new Project(id,name,date,progress);
            otherProjects.add(project);
        }
        mProjects.addAll(selfProjects);
        mProjects.addAll(otherProjects);
        mAdapter.setProjectses(mProjects);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public ProjectAndMember getProjectAndMember() {
        return mProjectAndMember;
    }


    private JellyListener jellyListener = new JellyListener() {
        @Override
        public void onCancelIconClicked() {
            if (TextUtils.isEmpty(mEditText.getText())) {
                mToolbar.collapse();
                mToolBarImageView.setVisibility(View.VISIBLE);
                mToolBarTitleTextView.setVisibility(View.VISIBLE);
            } else {
                mEditText.getText().clear();
            }
        }
        @Override
        public void onToolbarExpandingStarted() {
            super.onToolbarExpandingStarted();
            mToolBarImageView.setVisibility(View.GONE);
            mToolBarTitleTextView.setVisibility(View.GONE);
        }
    };

    @Override
    protected void initView() {
        setContentView(R.layout.activity_home_page);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mNavigationView=(NavigationView)findViewById(R.id.nv_layout);
        mRecyclerView=(RecyclerView)findViewById(R.id.project_recyclerView);
        mFloatingActionButton=(FloatingActionButton)findViewById(R.id.addProject);
        mToolBarImageView=(ImageView)findViewById(R.id.toolbarImage);
        mToolBarTitleTextView =(TextView)findViewById(R.id.toolbarTitle);
        mTagsText=(TextView) mNavigationView.getHeaderView(0).findViewById(R.id.personaltags);
        mRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.home_page_swipe_refresh);
        mAddProjectDialogFragment= AddProjectDialogFragment.newInstance();


        //根据实际用户数据显示该标签
        //mTagsText.setText("计算机软件");


        mToolbar = (JellyToolbar) findViewById(R.id.toolbar);
        mToolbar.setJellyListener(jellyListener);
        mEditText = (AppCompatEditText) LayoutInflater.from(this).inflate(R.layout.edit_text, null);
        mEditText.setBackgroundResource(R.color.colorTransparent);
        mToolbar.setContentView(mEditText);
    }

    @Override
    protected void initData() {
        super.initData();
        mProjects=new ArrayList<>();
        mHomePagePresenter=new HomePagePresenter(this,this);
        /*mProjects.add(new Project(1,getString(R.string.project1_title),new Date(),27));
        mProjects.add(new Project(1,getString(R.string.project3_title),new Date(),27));
        mProjects.add(new Project(1,getString(R.string.project3_title),new Date(),27));
        mProjects.add(new Project(1,getString(R.string.project3_title),new Date(),27));
        mProjects.add(new Project(1,getString(R.string.project3_title),new Date(),27));
        mProjects.add(new Project(1,getString(R.string.project3_title),new Date(),27));
        mProjects.add(new Project(1,getString(R.string.project3_title),new Date(),27));
        mProjects.add(new Project(1,getString(R.string.project3_title),new Date(),27));
        mProjects.add(new Project(1,getString(R.string.project3_title),new Date(),27));*/

        showProjects();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItem(item.getItemId());
                // 关闭侧滑菜单
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddProjectDialogFragment.show(getFragmentManager(),ADDPROJECTDIALOGFRAGMENT);
            }
        });

        mToolBarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(mNavigationView);
            }
        });

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHomePagePresenter.getProjects();
            }
        });
    }

    private void showProjects(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter=new HomePageAdapter(mProjects,HomePageActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mHomePagePresenter.getProjects();
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
    public void onFragmentInteraction(ProjectAndMember projectAndMember) {
        mProjectAndMember=projectAndMember;
        mHomePagePresenter.newProject();
    }

    private void selectItem(int itemid) {
        switch (itemid) {
            case R.id.menu_history:{
                Toast.makeText(HomePageActivity.this, "点击Fri", Toast.LENGTH_SHORT).show();
                //showProjects();
                break;
            }
            case R.id.menu_info: {
                Intent intent = new Intent(HomePageActivity.this,UserInfoActivity.class);
                startActivity(intent);
                Toast.makeText(HomePageActivity.this, "点击Thurs", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.menu_logout:{
                /*Intent intent = new Intent(HomePageActivity.this,MainActivity.class);
                startActivity(intent);*/
                break;
            }
            case R.id.menu_about: {
                Toast.makeText(HomePageActivity.this, "点击 Sat", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.menu_set: {
                Toast.makeText(HomePageActivity.this, "点击 Sun", Toast.LENGTH_SHORT).show();
                break;
            }
            default:
                break;
        }
    }
}