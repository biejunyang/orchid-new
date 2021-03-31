package com.orchid.core.exception;

import com.orchid.core.ResultCode;
import com.orchid.core.ResultCodeEnum;

public class ExceptionBuilder {

    public static BaseException build(ResultCode resultCode){
        return new BaseException(resultCode.code(), resultCode.msg());
    }


    public static BaseException build(String msg){
        return new BaseException(ResultCodeEnum.SERVER_ERROR.code(), msg);
    }

    public static BaseException build(int code, String msg){
        return new BaseException(code, msg);
    }
}
