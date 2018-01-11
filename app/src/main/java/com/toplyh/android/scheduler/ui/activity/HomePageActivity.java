package com.toplyh.android.scheduler.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.entity.remote.Project;
import com.toplyh.android.scheduler.service.entity.remote.ProjectAndMember;
import com.toplyh.android.scheduler.service.entity.remote.Projects;
import com.toplyh.android.scheduler.service.presenter.HomePagePresenter;
import com.toplyh.android.scheduler.service.utils.SharedPreferencesUtils;
import com.toplyh.android.scheduler.service.view.HomePageView;
import com.toplyh.android.scheduler.ui.adapter.HomePageAdapter;
import com.toplyh.android.scheduler.ui.fragment.AddProjectDialogFragment;
import com.yalantis.jellytoolbar.listener.JellyListener;
import com.yalantis.jellytoolbar.widget.JellyToolbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class HomePageActivity extends BaseActivity implements HomePageView,
        AddProjectDialogFragment.OnAddFragmentInteractionListener,SensorEventListener{

    private HomePagePresenter mHomePagePresenter;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FloatingActionButton mFloatingActionButton;

    private RecyclerView mRecyclerView;
    private HomePageAdapter mAdapter;
    private List<Project> mProjects;
    private List<Project> mSearchProjects;

    private TextView mTagsText;
    private ImageView mToolBarImageView;
    private TextView mToolBarTitleTextView;

    private JellyToolbar mToolbar;
    private AppCompatEditText mEditText;
    private AddProjectDialogFragment mAddProjectDialogFragment;
    private ProjectAndMember mProjectAndMember;

    private SwipeRefreshLayout mRefreshLayout;

    private static final String ADDPROJECTDIALOGFRAGMENT = "addprojectdialogfragment";


    //传感器管理器
    private SensorManager mSensorManager;

    //加速度传感器
    private Sensor mAccelerometerSensor;

    //是否震动
    private boolean isShake;

    //获取Bibrator震动服务
    private Vibrator mVibrator;

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
            String username=projects.getSelfProjects().get(i).getUsername();
            Project project=new Project(id,name,date,progress,username);
            selfProjects.add(project);
        }
        List<Project> otherProjects=new ArrayList<>();
        for (int i=0;i<projects.getOtherProjects().size();i++){
            Integer id=projects.getOtherProjects().get(i).getId();
            String name=projects.getOtherProjects().get(i).getProjectName();
            Date date=new Date(projects.getOtherProjects().get(i).getDdl());
            Integer progress=projects.getOtherProjects().get(i).getProgress();
            String username=projects.getOtherProjects().get(i).getUsername();
            Project project=new Project(id,name,date,progress,username);
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

    @Override
    public void shake() {
        mVibrator.vibrate(300);
    }

    @Override
    public void setShake(Boolean b) {
        isShake=b;
    }

    @Override
    public void openProjectFragment() {
        mAddProjectDialogFragment.show(getFragmentManager(),ADDPROJECTDIALOGFRAGMENT);
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
                showSearchProjects(mProjects);
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
            //getWindow().setNavigationBarColor(Color.TRANSPARENT);
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
        isShake=false;
        mVibrator=(Vibrator)getSystemService(VIBRATOR_SERVICE);
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

        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {


                    String projectName=mEditText.getText().toString();
                    for (Project project:mProjects) {
                        if(project.getProjectName().equals(projectName)){
                            mSearchProjects=new ArrayList<>();
                            mSearchProjects.add(project);
                            showSearchProjects(mSearchProjects);
                        }
                    }

                }
                return false;
            }
        });

    }

    private void showSearchProjects(List<Project> projects){
        mAdapter.setProjectses(projects);
        mAdapter.notifyDataSetChanged();
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
    public void setShakeFalse() {
        isShake=false;
    }

    @Override
    public void onFragmentInteraction(ProjectAndMember projectAndMember) {
        mProjectAndMember=projectAndMember;
        mHomePagePresenter.newProject();
    }

    private void selectItem(int itemid) {
        switch (itemid) {
            case R.id.menu_history:{
                Toast.makeText(HomePageActivity.this, "功能敬请期待！", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(HomePageActivity.this,MaterialLoginActivity.class);
                SharedPreferencesUtils.clearAll(HomePageActivity.this);
                startActivity(intent);
                finish();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHomePagePresenter.destroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        if (mSensorManager!=null){
            mAccelerometerSensor =mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (mAccelerometerSensor !=null){
                mSensorManager.registerListener(this,mAccelerometerSensor,SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSensorManager!=null){
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type=event.sensor.getType();

        if (type==Sensor.TYPE_ACCELEROMETER){
            float[] values=event.values;
            float x=values[0];
            float y=values[1];
            float z=values[2];

            if ((Math.abs(x)>17||Math.abs(y)>17||Math.abs(z)>17)&&!isShake){
                mHomePagePresenter.shake();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
