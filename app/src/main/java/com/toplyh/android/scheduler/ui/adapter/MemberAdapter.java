package com.toplyh.android.scheduler.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.entity.remote.Member;
import com.toplyh.android.scheduler.ui.view.MyProgressBar;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 我 on 2017/12/30.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder>{
    private List<Member> memberList;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView memberHeadPic;
        TextView memberName;
        MyProgressBar myProgressBar;
        CardView cardView;

        public ViewHolder(View view){
            super(view);
            memberHeadPic=(CircleImageView) view.findViewById(R.id.member_headpic);
            memberName=(TextView) view.findViewById(R.id.name);
            myProgressBar = (MyProgressBar) view.findViewById(R.id.progressbar);
            cardView = (CardView) view.findViewById(R.id.member);
        }
    }

    public void setMemberList(List<Member> memberList){
        this.memberList=memberList;
    }

    public MemberAdapter(List<Member> members){
        memberList=members;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_info, null, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Member member = memberList.get(position);
        Glide.with(mContext).load(R.drawable.image).into(holder.memberHeadPic);
        holder.memberName.setText(member.getName());
        holder.myProgressBar.setProgress(member.getContribution());
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }
}