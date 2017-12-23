package com.toplyh.android.scheduler.service.entity.local;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.relation.ToMany;

/**
 * Created by 我 on 2017/11/26.
 */
@Entity
public class User {
    @Id
    long id;

    @Index
    String username;

    String token;

    String nickname;

    @Backlink
    public ToMany<Skill> skills;

    public User(long id,String username,String token,String nickname){
        this.id=id;
        this.username=username;
        this.token=token;
        this.nickname=nickname;
    }

    public User(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ToMany<Skill> getSkills() {
        return skills;
    }
}
