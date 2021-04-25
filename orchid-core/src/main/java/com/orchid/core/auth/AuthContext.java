package com.orchid.core.auth;

import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * 用户认证上下文
 * @author biejunyang
 * @version 1.0
 * @date 2021/4/23 16:50
 */
public interface AuthContext {


    /**
     * 获取当前认证信息
     * @return
     */
    Authentication getAuthentication();
    /**
     * 获取当前登录用户
     * @return
     */
    Object getLoginUser();


    /**
     * 获取当前登录用户名
     * @return
     */
    String getUsername();


    /**
     * 获取用户角色列表
     * @return
     */
    List<String> getRoles();
    /**
     * 获取当前登录用户权限列表
     * @return
     */
    List<String> getAuthoritys();

    /**
     * 获取应用clientId
     * @return
     */
    String getClientId();


    /**
     * 判断当前登录用户是否有某资源的访问权限
     * @param authority
     * @return
     */
    boolean hasPermission(String authority);

    /**
     * 判断当前登录用户是否包含某个角色
     * @param roleCode
     * @return
     */
    boolean hasRole(String roleCode);

    /**
     * 判断当前登录用户是否包含任意一个角色
     * @param roleCodes
     * @return
     */
    boolean hasAnyRole(String roleCodes);


    /**
     * 判断当前登录用户是否包含所有角色
     * @param roleCodes
     * @return
     */
    boolean hasAllRole(String roleCodes);

    /**
     * 判断当前登录用户是否是超级管理员
     * @return
     */
    boolean isSuperAdmin();




}
