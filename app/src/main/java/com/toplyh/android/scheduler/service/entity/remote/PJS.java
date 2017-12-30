package com.toplyh.android.scheduler.service.entity.remote;

import java.util.List;

/**
 * Created by 我 on 2017/12/29.
 */

public class PJS {

    /**
     * data : {"otherProjects":[{"id":2,"projectName":"辣椒水","ddl":1511882038000,"progress":3}],"selfProjects":[{"id":3,"projectName":"和九","ddl":1514466559000,"progress":4},{"id":2,"projectName":"辣椒水","ddl":1511882038000,"progress":3},{"id":1,"projectName":"和九","ddl":1511882038000,"progress":2}]}
     * state : 510
     */

    private DataBean data;
    private int state;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public static class DataBean {
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
             * progress : 3
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

            public int getProgress() {
                return progress;
            }

            public void setProgress(int progress) {
                this.progress = progress;
            }
        }

        public static class SelfProjectsBean {
            /**
             * id : 3
             * projectName : 和九
             * ddl : 1514466559000
             * progress : 4
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

            public int getProgress() {
                return progress;
            }

            public void setProgress(int progress) {
                this.progress = progress;
            }
        }
    }
}
