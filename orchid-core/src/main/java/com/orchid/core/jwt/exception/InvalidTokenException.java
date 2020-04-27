package com.orchid.core.jwt.exception;

public class InvalidTokenException extends JwtTokenException {
    public InvalidTokenException() {
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}
