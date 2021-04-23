package com.orchid.resource.context;

import com.orchid.core.auth.AuthContext;
import com.orchid.core.auth.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * 用户认证上下文实现
 * @author biejunyang
 * @version 1.0
 * @date 2021/4/23 16:53
 */
public class AuthContextImpl implements AuthContext {


    @Override
    public AuthUser getLoginUser() {
        return (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public List<String> getRoles() {
        return null;
    }

    @Override
    public List<String> getAuthoritys() {
        return null;
    }

    @Override
    public String getClientId() {
        return null;
    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public boolean hasPermission(String authority) {
        return false;
    }

    @Override
    public boolean hasRole(String roleCode) {
        return false;
    }

    @Override
    public boolean hasAnyRole(String roleCodes) {
        return false;
    }

    @Override
    public boolean hasAllRole(String roleCodes) {
        return false;
    }

    @Override
    public boolean isSuperAdmin() {
        return false;
    }
}
