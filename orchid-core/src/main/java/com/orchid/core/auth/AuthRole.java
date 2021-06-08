package com.orchid.core.auth;


import java.io.Serializable;
import java.util.List;

/**
 * 认证角色信息
 */
public class AuthRole implements Serializable {

    private Long id;

    private String name;

    private String code;

    private List<AuthPrivilege> privileges;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<AuthPrivilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<AuthPrivilege> privileges) {
        this.privileges = privileges;
    }
}
