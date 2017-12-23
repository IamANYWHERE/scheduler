package com.toplyh.android.scheduler.service.entity.local;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by 我 on 2017/11/26.
 */
@Entity
public class Skill {
    @Id
    long id;

    String description;

    ToOne<User> user;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ToOne<User> getUser() {
        return user;
    }
}
