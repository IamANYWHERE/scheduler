package com.toplyh.android.scheduler.service.entity.remote;

import com.toplyh.android.scheduler.ui.adapter.MeetingAdapter;

import java.util.Date;
import java.util.List;

/**
 * Created by 我 on 2017/12/30.
 */

public class Meeting {

    private Integer id;

    private long date;

    private String name;

    private List<String> members;

    public Meeting(Date date,
                   Integer id,
                   String name,
                   List<String> members){
        this.date=date.getTime();
        this.id=id;
        this.name=name;
        this.members=members;

    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
