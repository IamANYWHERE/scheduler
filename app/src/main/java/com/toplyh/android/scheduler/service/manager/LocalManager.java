package com.toplyh.android.scheduler.service.manager;

import android.app.Activity;
import android.content.Context;

import com.toplyh.android.scheduler.app.App;
import com.toplyh.android.scheduler.service.entity.local.User;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by 我 on 2017/11/26.
 */

public class LocalManager {

    private BoxStore dataBox;
    public LocalManager(Context context){
        dataBox=((App)(((Activity)context).getApplication())).getBoxStore();
        Box<User> usersBox=dataBox.boxFor(User.class);
    }
}
