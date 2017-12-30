package com.toplyh.android.scheduler.service.entity.remote;

import java.util.Date;

/**
 * Created by 我 on 2017/12/27.
 */

public class Project {
    private Integer id;

    private String projectName;

    private Date ddl;

    private Integer progress;

    public Project(Integer id,String projectName,Date ddl,Integer progress){
        this.id=id;
        this.projectName=projectName;
        this.ddl=ddl;
        this.progress=progress;
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

    public Date getDdl() {
        return ddl;
    }

    public void setDdl(Date ddl) {
        this.ddl = ddl;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }
}
