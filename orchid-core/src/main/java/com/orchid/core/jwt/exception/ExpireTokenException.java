package com.orchid.core.jwt.exception;

public class ExpireTokenException extends JwtTokenException {
    public ExpireTokenException() {
    }

    public ExpireTokenException(String message) {
        super(message);
    }
}
