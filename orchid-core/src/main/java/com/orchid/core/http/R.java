package com.orchid.core.http;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * rest api 返回数据统一格式
 */
@Data
@AllArgsConstructor
public class R {
    public static final int SYS_SYSTEM = 100;

    public static final int CODE_SUCCESS=SYS_SYSTEM+0;//成功

    public static final int CODE_ERROR=SYS_SYSTEM+1;//失败

    public static final int CODE_LOGIN_TIMEOUT=SYS_SYSTEM+2;//登录超时


    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    public static R success() {
        return new R(CODE_SUCCESS, "success",null);
    }

    public static R success(Object data) {
        return new R(CODE_SUCCESS, "success", data);
    }

    public static R success(String msg, Object data) {
        return new R(CODE_SUCCESS, msg, data);
    }

    public static R error() {
        return new R(CODE_ERROR, "error", null);
    }

    public static R error(Object data) {
        return new R(CODE_ERROR,  "error", data);
    }
    public static R error(String msg) {
        return new R(CODE_ERROR,  msg, null);
    }
    public static R error(String msg, Object data) {
        return new R(CODE_ERROR,  msg, data);
    }

    public static R build(boolean success) {
        return success ? success() : error();
    }

}
