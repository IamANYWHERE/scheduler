package com.toplyh.android.scheduler.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.entity.remote.Project;
import com.toplyh.android.scheduler.service.utils.SharedPreferencesUtils;
import com.toplyh.android.scheduler.service.utils.TimeHelper;
import com.toplyh.android.scheduler.ui.activity.ProjectActivity;
import com.toplyh.android.scheduler.ui.test.ReactiveNoteActivity;

import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2017/11/28.
 */

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ProjectsViewHolder> {

    private List<Project> mProjectses;
    private Context mContext;

    public HomePageAdapter(List<Project> projects , Context context){
        this.mProjectses = projects;
        this.mContext = context;
    }

    public void setProjectses(List<Project> projectses) {
        mProjectses = projectses;
    }

    static class ProjectsViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView newsTitle;
        TextView newsDesc;
        NumberProgressBar mNumberProgressBar;

        public ProjectsViewHolder(final View itemView){
            super(itemView);
            cardView= (CardView) itemView.findViewById(R.id.card_view);
            imageView= (ImageView) itemView.findViewById(R.id.news_photo);
            newsTitle = (TextView) itemView.findViewById(R.id.news_title);
            newsDesc = (TextView) itemView.findViewById(R.id.news_desc);
            mNumberProgressBar=(NumberProgressBar) itemView.findViewById(R.id.projectsProgress);
        }

    }

    @Override
    public HomePageAdapter.ProjectsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.home_page_item,viewGroup,false);
        ProjectsViewHolder projectsViewHolder=new ProjectsViewHolder(v);
        return projectsViewHolder;
    }

    @Override
    public void onBindViewHolder(HomePageAdapter.ProjectsViewHolder personViewHolder, int i) {
        final int j=i;
        Project project=mProjectses.get(i);
        int state= TimeHelper.getDateDiff(new Date(),new Date(project.getDdl()));
        setProjectImage(state,personViewHolder);
        personViewHolder.newsTitle.setText(mProjectses.get(i).getProjectName());
        personViewHolder.newsDesc.setText("DDL:"+TimeHelper.getDateDiffDesc(new Date(),new Date(project.getDdl()))+"天");
        personViewHolder.mNumberProgressBar.setProgress(mProjectses.get(i).getProgress());
        personViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isleader;
                Project p=mProjectses.get(j);
                if (SharedPreferencesUtils.getParam(mContext,mContext.getString(R.string.username),"")
                        .equals(p.getUsername())){
                    isleader=true;
                }else {
                    isleader=false;
                }
                    Intent intent=ProjectActivity.newIntent(mContext,
                            isleader,
                            p.getId(),
                            p.getProjectName(),
                            p.getDdl(),
                            p.getProgress(),
                            p.getUsername());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mProjectses.size();
    }

    private void setProjectImage(int state,ProjectsViewHolder holder){
        if (state==0){
            Glide.with(mContext).load(R.drawable.emergency).into(holder.imageView);
        }else if (state==1){
            Glide.with(mContext).load(R.drawable.normal).into(holder.imageView);
        }else{
            Glide.with(mContext).load(R.drawable.start).into(holder.imageView);
        }
    }
}
