package com.orchid.core.exception;

import com.orchid.core.ResultCode;

public class InvalidTokenException extends JwtTokenException {

    public InvalidTokenException(ResultCode resultCode) {
        super(resultCode);
    }
}
