package com.orchid.core;


public class Result<T> {


    //状态：0失败,1成功
    private Status status;

    //业务状态码
    private int code;

    //提示消息
    private String msg;

    //返回数据
    private T data;

    private Result(Status status, int code, String msg, T data) {
        this.status = status;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }



    public static  <T> Result success(int code,String msg,T data){
        return new Result<>(Status.SUCCESS, code, msg, data);
    }



    public static  <T> Result success(int code,T data){
        return new Result<T>(Status.SUCCESS, code, ResultCodeEnum.OK.msg(), data);
    }


    public static  Result success(){
        return new Result<>(Status.SUCCESS, ResultCodeEnum.OK.code(), ResultCodeEnum.OK.msg(), null);
    }

    public static  <T> Result success(T data){
        return new Result<>(Status.SUCCESS, ResultCodeEnum.OK.code(), ResultCodeEnum.OK.msg(), data);
    }


    public static  <T> Result success(ResultCode resultCode){
        return new Result<T>(Status.SUCCESS, resultCode.code(), resultCode.msg(), null);
    }


    public static  Result success(String msg){
        return new Result<>(Status.SUCCESS, ResultCodeEnum.OK.code(), msg, null);
    }

    public static <T> Result success(ResultCode resultCode, T data){
        return new Result<>(Status.SUCCESS, resultCode.code(), resultCode.msg(), data);
    }


    public static Result error() {
        return new Result(Status.FAILURE, ResultCodeEnum.SERVER_ERROR.code(), ResultCodeEnum.SERVER_ERROR.msg(), null);
    }

    public static Result error(String msg) {
        return new Result(Status.FAILURE, ResultCodeEnum.SERVER_ERROR.code(), msg, null);
    }

    public static Result error(int code,String msg) {
        return new Result(Status.FAILURE, code, msg, null);
    }

    public static Result error(ResultCode resultCode) {
        return new Result(Status.FAILURE, resultCode.code(), resultCode.msg(), null);
    }

    public static Result build(boolean success) {
        return success ? success() : error();
    }


    public int getStatus() {
        return status.ordinal();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    enum Status{
        FAILURE,SUCCESS
    }

}
