package com.toplyh.android.scheduler.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.entity.remote.Sprint;
import com.toplyh.android.scheduler.service.utils.TimeHelper;

import java.util.Date;
import java.util.List;

/**
 * Created by Michael on 2017/12/21.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{

    private Context mContext;
    private List<Sprint> mList;
    private Boolean isLeader;

    public MyRecyclerViewAdapter(Context context, List<Sprint> list,Boolean isleader){
        this.mContext=context;
        this.mList=list;
        this.isLeader=isleader;
    }

    public void setSprints(List<Sprint> sprints){
        mList=sprints;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder= new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.unburned_sprint,parent,false));
        changeButtonText(viewType,viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.sprintName.setText(mList.get(position).getName());
        holder.sprintDDL.setText(TimeHelper.parseDateSimple(new Date(mList.get(position).getDdl()),"yyyy-MM-dd HH:mm"));
        holder.changeStateButton.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener{

        TextView sprintName;
        TextView sprintDDL;
        Button changeStateButton;
        public ViewHolder(View view){
            super(view);
            sprintName=(TextView)view.findViewById(R.id.spring_name);
            sprintDDL=(TextView)view.findViewById(R.id.spring_deadline);
            changeStateButton=(Button)view.findViewById(R.id.sprint_button);
            changeStateButton.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position=ViewHolder.this.getLayoutPosition();
        }
    }



    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getStatus().ordinal();
    }

    private void changeButtonText(int type, ViewHolder holder){
        if (type== Sprint.SprintStatus.BB.ordinal()) {
            holder.changeStateButton.setText(R.string.sprint_bb);
        }else if (type==Sprint.SprintStatus.BG.ordinal()){
            holder.changeStateButton.setText(R.string.sprint_bg);
        }else if (type==Sprint.SprintStatus.BD.ordinal()){
            holder.changeStateButton.setText(R.string.sprint_bd);
            if (isLeader){
                holder.changeStateButton.setEnabled(true);
            }else {
                holder.changeStateButton.setEnabled(false);
            }
        }else if (type== Sprint.SprintStatus.BO.ordinal()){
            holder.changeStateButton.setEnabled(false);
            holder.changeStateButton.setText(R.string.sprint_bo);
        }
    }


}
