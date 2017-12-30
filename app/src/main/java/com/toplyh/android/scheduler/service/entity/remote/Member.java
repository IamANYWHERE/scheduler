package com.toplyh.android.scheduler.service.entity.remote;

/**
 * Created by 我 on 2017/12/30.
 */

public class Member {

    private String nickName;

    private String name;

    private Integer contribution;

    public Member(String name,String nickName,Integer contribution){
        this.name=name;
        this.nickName=nickName;
        this.contribution=contribution;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getContribution() {
        return contribution;
    }

    public void setContribution(Integer contribution) {
        this.contribution = contribution;
    }
}
