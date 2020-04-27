package com.orchid.web.exception;

public class TokenInvalidException extends RuntimeException {

    public TokenInvalidException(){
        super("invalid token");
    }

    public TokenInvalidException(String msg){
        super(msg);
    }
}
