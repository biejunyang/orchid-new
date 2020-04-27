package com.orchid.core.jwt.exception;

public class JwtTokenException extends RuntimeException {
    public JwtTokenException() {
    }

    public JwtTokenException(String message) {
        super(message);
    }
}
