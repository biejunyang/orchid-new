package com.orchid.core;

public enum  ResultEnum implements ResultCode {

    OK(200, "ok"),

    SERVER_ERROR(500, "服务器错误"),

    BUSINESS_PARAM_ERROR(400, "业务参数错误");


    private int code;

    private String msg;


    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }



    @Override
    public int code() {
        return this.code;
    }

    @Override
    public String msg() {
        return this.msg;
    }
}
