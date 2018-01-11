package com.toplyh.android.scheduler.service.entity.remote;

/**
 * Created by 我 on 2017/12/31.
 */

public class AddSprint {

    private int projectId;
    private String memberName;
    private SprintBean sprint;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public SprintBean getSprint() {
        return sprint;
    }

    public void setSprint(SprintBean sprint) {
        this.sprint = sprint;
    }

    public static class SprintBean {
        /**
         * id : null
         * name : 吃东西
         * content : ssssss
         * ddl : 1512289865171
         * status : BO
         * workTime : 3
         */

        private String name;
        private String content;
        private long ddl;
        private String status;
        private int workTime;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getWorkTime() {
            return workTime;
        }

        public void setWorkTime(int workTime) {
            this.workTime = workTime;
        }
    }
}
