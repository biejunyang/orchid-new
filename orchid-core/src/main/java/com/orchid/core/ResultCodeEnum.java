package com.orchid.core;

public enum ResultCodeEnum implements ResultCode {

    OK(200, "ok"),

    SERVER_ERROR(500, "服务器错误"),
    REPEAT_COMMIT_ERROR(301, "重复提交"),
    BUSINESS_PARAM_ERROR(400, "业务参数错误"),

    NOT_LOGIN_ERROR(401, "未登录"),
    TOKEN_INVALID_ERROR(402, "Token错误"),
    TOKEN_EXPIRED_ERROR(402, "Token过期"),

    ;



    private int code;
    private String msg;


    ResultCodeEnum(int code, String msg) {
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
