package com.orchid.core.exception;

import com.orchid.core.ResultCode;

public class JwtTokenException extends BaseException {

    public JwtTokenException(ResultCode resultCode) {
        super(resultCode);
    }
}
