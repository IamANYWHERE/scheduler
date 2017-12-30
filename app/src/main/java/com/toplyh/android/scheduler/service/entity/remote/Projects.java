package com.toplyh.android.scheduler.service.entity.remote;

import java.util.Date;
import java.util.List;

/**
 * Created by 我 on 2017/12/28.
 */

public class Projects {


    private List<OtherProjectsBean> otherProjects;
    private List<SelfProjectsBean> selfProjects;

    public List<OtherProjectsBean> getOtherProjects() {
        return otherProjects;
    }

    public void setOtherProjects(List<OtherProjectsBean> otherProjects) {
        this.otherProjects = otherProjects;
    }

    public List<SelfProjectsBean> getSelfProjects() {
        return selfProjects;
    }

    public void setSelfProjects(List<SelfProjectsBean> selfProjects) {
        this.selfProjects = selfProjects;
    }

    public static class OtherProjectsBean {
        /**
         * id : 2
         * projectName : 辣椒水
         * ddl : 1511882038000
         * progress : null
         */

        private int id;
        private String projectName;
        private long ddl;
        private int progress;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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
    }

    public static class SelfProjectsBean {
        /**
         * id : 2
         * projectName : 辣椒水
         * ddl : 1511882038000
         * progress : null
         */

        private int id;
        private String projectName;
        private long ddl;
        private int progress;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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
    }
}
