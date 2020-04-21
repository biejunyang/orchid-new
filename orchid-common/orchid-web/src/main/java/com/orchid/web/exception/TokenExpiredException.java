package com.orchid.web.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(){
        super("expired token");
    }
    public TokenExpiredException(String msg){
        super(msg);
    }
}
