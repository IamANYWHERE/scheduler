package com.toplyh.android.scheduler.service.view;

import com.toplyh.android.scheduler.service.entity.remote.Member;
import com.toplyh.android.scheduler.service.entity.remote.MetAndMem;
import com.toplyh.android.scheduler.service.entity.remote.Project;
import com.toplyh.android.scheduler.service.entity.remote.Sprint;

import java.util.Date;
import java.util.List;

/**
 * Created by 我 on 2017/12/30.
 */

public interface ProjectView extends View {

    Integer getProjectId();
    Sprint.SprintStatus getStatus();
    void showSprintRefreshDialog();
    void cancelSprintRefreshDialog();
    void initSprintView(List<Sprint> sprints);

    String getSprintName();
    String getSprintContent();
    Integer getSprintWorkTime();
    String getSprintMember();
    long getSprintDDL();
    Sprint.SprintStatus getNewSprintStatus();

    Integer getChangeSprintId();
    Sprint.SprintStatus getChangeSprintStatus();

    void showMeetingRefreshDialog();
    void cancelMeetingRefreshDialog();
    void initMeetingView(List<MetAndMem> metAndMems);

    String getMeetingName();
    List<String> getMeetingMembers();
    long getMeetingDate();

    void initMemberView(List<Member> members);
    void showMemberRefreshDialog();
    void cancelMemberRefreshDialog();

    void initProgressView(Integer progress);
    void showProgressRefreshDialog();
    void cancelProgressRefreshDialog();
}
