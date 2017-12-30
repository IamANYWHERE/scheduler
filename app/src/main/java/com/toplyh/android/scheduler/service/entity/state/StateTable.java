package com.toplyh.android.scheduler.service.entity.state;

/**
 * Created by 我 on 2017/11/23.
 */

public class StateTable {
    public static final int AUTHENTICATION_TOKEN_ERROR=444;
    /**
     *AuthenticationApi中状态常量
     */
    public static final int LOGIN_RIGHT=100;
    //登录正常
    public static final int LOGIN_NAME_ERROR=101;
    //登录名字错误
    public static final int LOGIN_PASSWORD_ERROR=102;
    //登录密码错误
    public static final int UPDATE_USER_RIGHT=150;
    //跟新账号正常
    public static final int UPDATE_USER_PASSWORD_ERROR=151;
    //密码错误
    public static final int GET_MSG_RIGHT=160;
    //获取用户信息正确
    /**
     * UserApi
     */
    public static final int REGISTER_RIGHT=200;
    //注册正常
    public static final int REGISTER_ERROR=201;
    //注册名字已有

    /**
     * Skill相关状态参数
     */
    public static final int SKILL_ADD_RIGHT=300;
    //添加技能正常
    public static final int SKILL_SHOW_RIGHT=310;
    //返回所有技能正常

    /**
     * project相关状态
     */
    public static final int PROJECT_ADD_RIGHT=500;
    //添加项目正常
    public static final int PROJECT_SHOW_RIGHT=510;
    //返回所有项目正常
    /**
     * story
     */
    public static final int STORY_NO_PROJECT =601;
    //没有这个项目
    public static final int STORY_ADD_RIGHT=600;
    //正确添加
    public static final int STORY_SHOW_RIGHT=610;
    //展示
    public static final int STORY_NO_STORY=602;

    /**
     * member
     */
    public static final int MEMBER_NO_PROJECT=701;
    //没这个项目
    public static final int MEMBER_ADD_RIGHT=700;
    //添加正确
    public static final int MEMBER_SHOW_RIGHT=710;
    //展示
    public static final int MEMBER_NO_MEMBER=702;

    /**
     * meeting
     */
    public static final int MEETING_NO_PROJECT=801;
    //没有项目
    public static final int MEETING_ADD_RIGHT=800;
    //添加正确
    public static final int MEETING_SHOW_RIGHT=810;
    //展示
    public static final int MEETING_NO_MEETING=802;
    //没有这个会议
    public static final int MEETING_ADD_MEMBER_RIGHT=820;
    //会议添加成员成功
    /**
     * sprint
     */
    public static final int SPRINT_NO_PROJECT=901;
    //没有项目
    public static final int SPRINT_NO_MEMBER=903;
    //没有成员
    public static final int SPRINT_ADD_RIGHT=900;
    //添加正确
    public static final int SPRINT_SHOW_RIGHT=910;
    //展示
    public static final int SPRINT_NO_SPRINT=902;
    //没有这个冲刺

    /**
     * WebSocket
     */
    public static final int WEB_SOCKET_ADD_MEETING_RIGHT=1001;
}
