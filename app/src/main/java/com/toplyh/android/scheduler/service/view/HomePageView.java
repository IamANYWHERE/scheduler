package com.toplyh.android.scheduler.service.view;

import com.toplyh.android.scheduler.service.entity.remote.PJS;
import com.toplyh.android.scheduler.service.entity.remote.ProjectAndMember;
import com.toplyh.android.scheduler.service.entity.remote.Projects;

import java.util.Date;
import java.util.List;

/**
 * Created by 我 on 2017/12/27.
 */

public interface HomePageView extends View {

    void toProjectPage();
    void toPersonalInfoPage();
    void toHistoryPage();
    void toAbout();
    void toSet();
    void toLoginPage();
    void initRecyclerView(Projects projects);
    ProjectAndMember getProjectAndMember();
}
