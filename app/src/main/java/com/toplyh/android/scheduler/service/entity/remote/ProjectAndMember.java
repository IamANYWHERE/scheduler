package com.toplyh.android.scheduler.service.entity.remote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 我 on 2017/12/28.
 */

public class ProjectAndMember {

    private String projectName;

    private long ddl;

    private List<String> members;

    public ProjectAndMember(){
        members=new ArrayList<>();
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

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
