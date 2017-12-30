package com.toplyh.android.scheduler.service.view;

import com.toplyh.android.scheduler.service.entity.remote.Project;
import com.toplyh.android.scheduler.service.entity.remote.Sprint;

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
}
