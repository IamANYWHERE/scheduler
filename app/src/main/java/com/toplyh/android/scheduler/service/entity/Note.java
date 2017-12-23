package com.toplyh.android.scheduler.service.entity;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by 我 on 2017/11/25.
 */
@Entity
public class Note {

    @Id
    long id;

    String text;
    String comment;
    Date date;

    public Note(long id,String text,String comment,Date date){
        this.id=id;
        this.text=text;
        this.comment=comment;
        this.date=date;
    }

    public Note(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
