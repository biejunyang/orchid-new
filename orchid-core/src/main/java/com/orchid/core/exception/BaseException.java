package com.orchid.core.exception;


import com.orchid.core.Result;
import com.orchid.core.ResultCode;

public class BaseException extends RuntimeException implements ResultCode {
    private int code;
    private String msg;

    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(String msg) {
        this.code= Result.ERROR;
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
