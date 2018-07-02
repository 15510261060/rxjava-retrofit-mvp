package com.senon.mvpretrofitrx.mvp.entity;

/**
 * 作者：senon on 2017/12/27 10:31
 *
 */

public class Login {

    private String time;
    private String ftime;
    private String context;
    private Object location;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Login{" +
                "time='" + time + '\n' +
                ", ftime='" + ftime + '\n' +
                ", context='" + context + '\n' +
                ", location=" + location +
                '}';
    }
}
