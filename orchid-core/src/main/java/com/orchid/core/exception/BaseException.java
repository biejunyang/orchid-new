package com.orchid.core.exception;


import com.orchid.core.ResultCode;
import com.orchid.core.ResultEnum;

public class BaseException extends RuntimeException implements ResultCode {
    private int code;
    private String msg;

    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(String msg) {
        super(msg);
        this.code= ResultEnum.SERVER_ERROR.code();
        this.msg = msg;
    }

    public BaseException(ResultCode resultCode) {
        super(resultCode.msg());
        this.code= resultCode.code();
        this.msg = resultCode.msg();
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
