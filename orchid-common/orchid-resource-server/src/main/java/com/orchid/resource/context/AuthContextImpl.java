package com.orchid.resource.context;

import cn.hutool.core.collection.CollectionUtil;
import com.orchid.core.auth.AuthContext;
import com.orchid.core.auth.AuthUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户认证上下文实现
 * @author biejunyang
 * @version 1.0
 * @date 2021/4/23 16:53
 */
public class AuthContextImpl implements AuthContext {

    private static final String SUPER_ADMIN="admin";

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public AuthUser getLoginUser() {
        Authentication authentication=getAuthentication();
        if(authentication!= null && authentication.isAuthenticated()){
            return (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return null;
    }

    @Override
    public String getUsername() {
        return getLoginUser()!=null ? getLoginUser().getUsername(): null;
    }

    @Override
    public List<String> getRoles() {
        return null;
    }

    @Override
    public List<String> getAuthoritys() {
        AuthUser authUser=getLoginUser();
        if(authUser!=null && authUser.getAuthorities()!=null){
            if(getLoginUser().getAuthorities()!=null){
                return getLoginUser().getAuthorities().parallelStream()
                        .map(au -> au.getAuthority()).collect(Collectors.toList());
            }
        }
        return null;
    }

    @Override
    public String getClientId() {
        OAuth2Authentication authentication=(OAuth2Authentication)getAuthentication();
        return authentication.getOAuth2Request().getClientId();
    }


    @Override
    public boolean hasPermission(String authority) {
        List<String> authorityes=getAuthoritys();
        return CollectionUtil.isNotEmpty(authorityes) && authorityes.contains(authority);
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
        return SUPER_ADMIN.equals(getUsername());
    }
}
