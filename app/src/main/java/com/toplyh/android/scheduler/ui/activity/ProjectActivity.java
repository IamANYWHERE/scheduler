package com.toplyh.android.scheduler.ui.activity;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dualcores.swagpoints.SwagPoints;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.jpeng.jptabbar.JPTabBar;
import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.entity.remote.Meeting;
import com.toplyh.android.scheduler.service.entity.remote.Member;
import com.toplyh.android.scheduler.service.entity.remote.Sprint;

import com.toplyh.android.scheduler.ui.adapter.MeetingAdapter;
import com.toplyh.android.scheduler.ui.adapter.MemberAdapter;
import com.toplyh.android.scheduler.ui.adapter.MyRecyclerViewAdapter;
import com.toplyh.android.scheduler.ui.adapter.MyViewPagerAdapter;
import com.toplyh.android.scheduler.ui.fragment.AddMeetingFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectActivity extends BaseActivity implements AddMeetingFragment.TimeCallback{

    private Boolean isLeader;

    private JPTabBar mTabbar;
    //下面bar
    private ViewPager mViewPager;
    //页卡内容
    private List<View> views;
    //tab页面列表
    private View view1,view2,view3,view4,view5;
    //各页卡
    private int offset = 0;
    // 动画图片偏移量
    private int currIndex = 0;
    // 当前页卡编号
    private int bmpW;
    // 动画图片宽度
    private ImageView imageView;
    // 动画图片

    //会议
    private RecyclerView meetingRecyclerView;

    private List<Meeting> meetingList=new ArrayList<>();

    private MeetingAdapter meetingAdapter;

    private SwipeRefreshLayout meetingRefresh;

    private Date date;
    private FloatingActionsMenu floatingActionsMenu;
    private FloatingActionButton actionButton1;
    private FloatingActionButton actionButton2;

    //成员界面
    private List<Member> memberList = new ArrayList<>();
    private RecyclerView memberRecyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout memberRefresh;
    private MemberAdapter memberAdapter;

    //进度页面
    private SwagPoints swagPoints;
    private SwipeRefreshLayout progressRefresh;

    //详情
    private List<String> mTitleList;// = new ArrayList<>();
    private List<android.view.View> fragments;// = new ArrayList<>();
    RadioGroup main_top_rg;
    RadioButton top_rg_a;
    RadioButton top_rg_b;
    RadioButton top_rg_c;
    RadioButton top_rg_d;
    private RecyclerView mRecyclerView;
    private List<Sprint> mSprintList = new ArrayList<>();


    @Override
    protected void initView() {
        setContentView(R.layout.activity_project);
        initViewPager();
        mTabbar=(JPTabBar)findViewById(R.id.tabbar);
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
    protected void initData() {
        isLeader=false;
        initMeeting();
        meetingAdapter = new MeetingAdapter(meetingList);
        meetingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        meetingRecyclerView.setAdapter(meetingAdapter);
        meetingRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        initMember();
        memberAdapter = new MemberAdapter(memberList);
        layoutManager = new LinearLayoutManager(this);
        memberRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        memberRecyclerView.setAdapter(memberAdapter);
        memberRefresh = (SwipeRefreshLayout) view3.findViewById(R.id.member_refresh);
        memberRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        swagPoints.setEnabled(false);
        setTotalProgress(40);
        mViewPager.setAdapter(new MyViewPagerAdapter(views));
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
            int two = one * 2;// 页卡1 -> 页卡3 偏移量
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Animation animation = new TranslateAnimation(one*currIndex, one*position, 0, 0);//显然这个比较简洁，只有一行代码。
                currIndex = position;
                animation.setFillAfter(true);// True:图片停在动画结束位置
                animation.setDuration(300);
                //imageView.startAnimation(animation);
                Toast.makeText(ProjectActivity.this, "您选择了"+ mViewPager.getCurrentItem()+"页卡", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fragments=new ArrayList<View>();
        mTitleList=new ArrayList<>();
        mSprintList.add(new Sprint(2,"ww","eeeasds",new Date(),Sprint.SprintStatus.BB,4));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this, mSprintList,isLeader);
        mRecyclerView.setAdapter(adapter);
        fragments.add(view5);
        mTitleList.add("unburned");
        main_top_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == top_rg_a.getId())
                {
                    mSprintList.add(new Sprint(2,"asd","qwdasd",new Date(), Sprint.SprintStatus.BB,3));
                    adapter.notifyDataSetChanged();

                }
                else if (checkedId == top_rg_b.getId())
                {

                }
                else if(checkedId == top_rg_c.getId()) {

                }
                else if(checkedId == top_rg_d.getId()) {

                }
            }
        });
        mTabbar.setTitles(R.string.tab1, R.string.tab2, R.string.tab3, R.string.tab4)
                .setNormalIcons(R.drawable.tab1_normal, R.drawable.tab2_normal, R.drawable.tab3_normal, R.drawable.tab4_normal)
                .setSelectedIcons(R.drawable.tab1_selected, R.drawable.tab2_selected, R.drawable.tab3_selected, R.drawable.tab4_selected)
                .generate();
        mTabbar.setContainer(mViewPager);
    }

    private void initViewPager(){
        mViewPager =(ViewPager) findViewById(R.id.vPager);
        views=new ArrayList<View>();
        LayoutInflater inflater=getLayoutInflater();
        view1=inflater.inflate(R.layout.project_detail, null);
        //会议界面
        view2=inflater.inflate(R.layout.project_meeting, null);

        meetingRecyclerView = (RecyclerView) view2.findViewById(R.id.meeting_list);
        meetingRefresh = (SwipeRefreshLayout) view2.findViewById(R.id.meeting_refresh);
        floatingActionsMenu = (FloatingActionsMenu) view2.findViewById(R.id.multiple_actions);
        actionButton1 = (FloatingActionButton) view2.findViewById(R.id.action_a);
        actionButton2 = (FloatingActionButton) view2.findViewById(R.id.action_b);

        //成员界面
        view3=inflater.inflate(R.layout.member_list, null);
        memberRecyclerView = (RecyclerView) view3.findViewById(R.id.member_list);
        //进度界面
        view4=inflater.inflate(R.layout.project_progress, null);
        swagPoints = (SwagPoints) view4.findViewById(R.id.total_progress);
        progressRefresh=(SwipeRefreshLayout)view4.findViewById(R.id.progress_refresh);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);

        //详情页面设置
        main_top_rg=(RadioGroup) view1.findViewById(R.id.main_top_rg);
        top_rg_a=(RadioButton) view1.findViewById(R.id.btn_unbruned);
        top_rg_b=(RadioButton) view1.findViewById(R.id.btn_burning);
        top_rg_c=(RadioButton) view1.findViewById(R.id.btn_burned);
        top_rg_d=(RadioButton) view1.findViewById(R.id.btn_ashed);
        view5=inflater.inflate(R.layout.fragment_state, null);
        mRecyclerView = (RecyclerView) view5.findViewById(R.id.recycler_view);
    }
    private void initMeeting() {
        meetingList.add(new Meeting(new Date(),2,"吃饭",new ArrayList<String>()));
        meetingList.add(new Meeting(new Date(),3,"吃屎",new ArrayList<String>()));
        meetingList.add(new Meeting(new Date(),2,"吃蛋糕",new ArrayList<String>()));
    }
    private void initMember() {
        Member m = new Member("george","无敌", 50);
        memberList.add(m);
        Member m2 = new Member("kuhn", "qwdq", 70);
        memberList.add(m2);

    }
    private void setTotalProgress(int progress) {
        swagPoints.setPoints(progress);
    }

    @Override
    public void sendDateAndTime(Date date) {
        this.date=date;
    }
}
