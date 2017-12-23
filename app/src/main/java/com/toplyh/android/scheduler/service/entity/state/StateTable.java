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
}
