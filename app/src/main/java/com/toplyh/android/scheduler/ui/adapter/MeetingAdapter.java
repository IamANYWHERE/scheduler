package com.toplyh.android.scheduler.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;
import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.entity.remote.Meeting;
import com.toplyh.android.scheduler.service.utils.TimeHelper;
import com.toplyh.android.scheduler.service.utils.VectorDrawableUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by 我 on 2017/12/30.
 */

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder>{

    private List<Meeting> meetingList;
    private Context context;

    public enum MeetingStatus{
        COMPLETED,
        ACTIVE,
        INACTIVE
    }

    public void setMeetingList(List<Meeting> meetingList){
        this.meetingList=meetingList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_info,parent,false);
        MeetingAdapter.ViewHolder holder = new MeetingAdapter.ViewHolder(view, viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Meeting meeting = meetingList.get(position);
        if (TimeHelper.getDatePassed(new Date(),new Date(meeting.getDate()))==MeetingStatus.INACTIVE){
            holder.timelineView.setMarker(VectorDrawableUtils.getDrawable(context,R.drawable.ic_marker_inactive,R.color.color_green_dark));
        }else if (TimeHelper.getDatePassed(new Date(),new Date(meeting.getDate()))==MeetingStatus.ACTIVE){
            holder.timelineView.setMarker(VectorDrawableUtils.getDrawable(context,R.drawable.ic_marker_active,R.color.color_yellow));
        }else if (TimeHelper.getDatePassed(new Date(),new Date(meeting.getDate()))==MeetingStatus.COMPLETED){
            holder.timelineView.setMarker(ContextCompat.getDrawable(context,R.drawable.ic_marker), ContextCompat.getColor(context,R.color.color_gray_dark));
        }
        holder.dateView.setText(TimeHelper.parseDateSimple(new Date(meeting.getDate()),"hh:mm a,dd-MMM-yyyy"));
        holder.nameView.setText(meeting.getName());
        holder.memberView.setText("");
        for (String s: meeting.getMembers()) {
            holder.memberView.setText(holder.memberView.getText()+s+";");
        }
    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TimelineView timelineView;
        TextView nameView;
        TextView dateView;
        TextView memberView;
        CardView cardView;

        public ViewHolder(View view,int viewType){
            super(view);
            timelineView=(TimelineView)view.findViewById(R.id.time_marker);
            nameView=(TextView)view.findViewById(R.id.meeting_name);
            dateView=(TextView)view.findViewById(R.id.meeting_time);
            memberView=(TextView)view.findViewById(R.id.meeting_member);
            cardView=(CardView)view.findViewById(R.id.meeting_cardview);
            timelineView.initLine(viewType);
        }
    }

    public MeetingAdapter(List<Meeting> meetings) {
        this.meetingList=meetings;
    }


}
