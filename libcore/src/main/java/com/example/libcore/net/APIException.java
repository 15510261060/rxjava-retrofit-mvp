package com.example.libcore.net;

/**
* 接口异常信息bean
* @auth hebin
* created at  
*/
public class APIException extends RuntimeException {

    private int code;
    private String msg;

    public APIException(int code, String msg){
        this.code=code;
        this.msg=msg;

    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
