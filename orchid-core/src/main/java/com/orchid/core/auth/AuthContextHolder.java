package com.orchid.core.auth;

import cn.hutool.extra.spring.SpringUtil;

/**
 * @author biejunyang
 * @version 1.0
 * @date 2021/6/9 10:20
 */
public class AuthContextHolder {

    public static AuthContext getContext(){
        return SpringUtil.getBean(AuthContext.class);
    }
}
