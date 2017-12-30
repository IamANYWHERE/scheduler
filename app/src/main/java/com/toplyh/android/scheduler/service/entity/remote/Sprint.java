package com.toplyh.android.scheduler.service.entity.remote;

import java.util.Date;

/**
 * Created by 我 on 2017/12/30.
 */

public class Sprint {
    public static enum SprintStatus{
        BB,BG,BD,BO
    }
    private Integer id;

    private String name;

    private String content;

    private long ddl;

    private SprintStatus status;

    private Integer workTime;

    public Sprint(){

    }

    public Sprint(Integer id,String name,String content,Date ddl,SprintStatus status,Integer workTime){
        this.id=id;
        this.name=name;
        this.content=content;
        this.ddl=ddl.getTime();
        this.status=status;
        this.workTime=workTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDdl() {
        return ddl;
    }

    public void setDdl(long ddl) {
        this.ddl = ddl;
    }

    public SprintStatus getStatus() {
        return status;
    }

    public void setStatus(SprintStatus status) {
        this.status = status;
    }

    public Integer getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Integer workTime) {
        this.workTime = workTime;
    }
}
