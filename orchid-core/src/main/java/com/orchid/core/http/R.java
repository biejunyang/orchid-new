package com.orchid.core.http;

import lombok.Data;

/**
 * rest api 返回数据统一格式
 */
@Data
public class R {



    /**
     * 状态码
     */
    private int code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;


    private R(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private R(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }








    public static R success() {
        return new R(CodeMessage.SUCCESS.getCode(), CodeMessage.SUCCESS.getMessage(),null);
    }

    public static R success(Object data) {
        return new R(CodeMessage.SUCCESS.getCode(), CodeMessage.SUCCESS.getMessage(),data);
    }

    public static R success(String msg, Object data) {
        return new R(CodeMessage.SUCCESS.getCode(), msg, data);
    }



    public static R error() {
        return new R(CodeMessage.SERVER_ERROR.getCode(), CodeMessage.SERVER_ERROR.getMessage(), null);
    }

    public static R error(Object data) {
        return new R(CodeMessage.SERVER_ERROR.getCode(), CodeMessage.SERVER_ERROR.getMessage(), data);
    }
    public static R error(String msg) {
        return new R(CodeMessage.SERVER_ERROR.getCode(),  msg, null);
    }
    public static R error(String msg, Object data) {
        return new R(CodeMessage.SERVER_ERROR.getCode(),  msg, data);
    }

    public static  R error(CodeMessage codeMessage){
        return new R(codeMessage.getCode(), codeMessage.getMessage());
    }

    public static  R error(CodeMessage codeMessage, Object data){
        return new R(codeMessage.getCode(), codeMessage.getMessage(), data);
    }


    public static R build(boolean success) {
        return success ? success() : error();
    }


}
