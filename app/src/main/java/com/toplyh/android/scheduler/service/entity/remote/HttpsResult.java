package com.toplyh.android.scheduler.service.entity.remote;

/**
 * Created by 我 on 2017/12/4.
 */

public class HttpsResult<T> {

    private int state;

    private T data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpsResult{"+
                "state="+state+
                "data="+data+
                "}";
    }
}

