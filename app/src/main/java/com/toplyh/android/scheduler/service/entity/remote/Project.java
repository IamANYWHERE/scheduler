package com.toplyh.android.scheduler.service.entity.remote;

import java.util.Date;

/**
 * Created by 我 on 2017/12/27.
 */

public class Project {
    private Integer id;

    private String projectName;

    private long ddl;

    private Integer progress;

    private String username;

    public Project(Integer id,String projectName,Date ddl,Integer progress,String username){
        this.id=id;
        this.projectName=projectName;
        this.ddl=ddl.getTime();
        this.progress=progress;
        this.username=username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public long getDdl() {
        return ddl;
    }

    public void setDdl(long ddl) {
        this.ddl = ddl;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
