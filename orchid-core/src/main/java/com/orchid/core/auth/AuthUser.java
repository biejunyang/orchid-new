package com.orchid.core.auth;


import cn.hutool.core.collection.CollectionUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 认证用户信息
 */
public class AuthUser implements Serializable {

    //用户id
    private Long id;

    //用户名
    private String username;

    //密码
//    private String password;

    //姓名
    private String realName;

    //昵称
    private String nickName;

    //出生日期
    private Date birthday;

    //性别：1男，2女，3不明
    private Integer sex;

    //邮箱
    private String email;

    //手机号
    private String phone;

    //其他自定义附加信息
    private Map<String, Object> details=new HashMap<>();

    //角色列表
    private List<String> roles = CollectionUtil.newArrayList();

    //权限列表
    private List<String> authorities = CollectionUtil.newArrayList();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }
}
