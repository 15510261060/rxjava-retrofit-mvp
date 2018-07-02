package com.example.libcore.net;

/**
* 接口响应bean
* @auth hebin
* TODO 待确认
*/
public class MAPIResult<T> {
    private String message;
    private String nu;
    private int ischeck;
    private String condition;
    private String com;
    private int status;
    private int state;
    private T data;

    public boolean isSuccess() {
        return status == 200;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public int getIscheck() {
        return ischeck;
    }

    public void setIscheck(int ischeck) {
        this.ischeck = ischeck;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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
        return "MAPIResult{" +
                "message='" + message + '\'' +
                ", nu='" + nu + '\'' +
                ", ischeck=" + ischeck +
                ", condition='" + condition + '\'' +
                ", com='" + com + '\'' +
                ", status=" + status +
                ", state=" + state +
                ", data=" + data +
                '}';
    }
}
