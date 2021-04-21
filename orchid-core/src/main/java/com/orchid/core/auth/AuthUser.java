package com.orchid.core.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 认证用户信息
 */
public class AuthUser implements UserDetails {

    //用户名
    private String username;

    //密码
    private String password;

    //是否过期：0否；1是
    private Integer expired;

    //是否锁住：0否；1是
    private Integer locked;

    //是否禁用：0否；1是
    private Integer disabled;

    //权限列表
    private List<SimpleGrantedAuthority> authorities;


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getExpired() {
        return expired;
    }

    public void setExpired(Integer expired) {
        this.expired = expired;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.expired ==  null || this.expired.equals(0);
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.locked ==  null || this.locked.equals(0);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.disabled == null || this.disabled.equals(0);
    }
}
