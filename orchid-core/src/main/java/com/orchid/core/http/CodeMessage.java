package com.orchid.core.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 状态码消息
 */

public class CodeMessage {
    private int code;
    private String message;


    //    public static final int SYS_SYSTEM = 100;
//
//    public static final int CODE_SUCCESS=SYS_SYSTEM+0;//成功
//
//    public static final int CODE_ERROR=SYS_SYSTEM+1;//失败
//
//    public static final int CODE_LOGIN_TIMEOUT=SYS_SYSTEM+2;//登录超时




    //返回成功状态码
    public static CodeMessage SUCCESS=new CodeMessage(0, "success");


    //通用异常状态码 500100
    public static CodeMessage SERVER_ERROR=new CodeMessage(500100, "服务端异常");

    //登录模块异常状态码 500200
    public static CodeMessage LOGIN_ERROR=new CodeMessage(500201, "登录失败");
    public static CodeMessage LOGIN_USERNAME_ERROR=new CodeMessage(500202, "用户名不存在");
    public static CodeMessage LOGIN_PASSWORD_ERROR=new CodeMessage(500202, "密码错误");



    public CodeMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
