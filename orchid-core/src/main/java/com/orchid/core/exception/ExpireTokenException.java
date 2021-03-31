package com.orchid.core.exception;

import com.orchid.core.ResultCode;
import com.orchid.core.ResultCodeEnum;

public class ExpireTokenException extends JwtTokenException {
    public ExpireTokenException(ResultCode resultCode) {
        super(ResultCodeEnum.TOKEN_EXPIRED_ERROR);
    }
}
