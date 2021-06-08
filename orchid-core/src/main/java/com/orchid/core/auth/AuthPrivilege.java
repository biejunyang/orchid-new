package com.orchid.core.auth;


import cn.hutool.core.util.StrUtil;
import com.orchid.core.model.TreeNode;

import java.io.Serializable;
import java.util.List;

/**
 * 认证权限信息
 */
public class AuthPrivilege implements Serializable, TreeNode {

    private Long id;

    private String name;

    private String code;

    private String pids;

    private List<AuthPrivilege> children;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Object getPid() {
        if(StrUtil.isNotEmpty(this.getPids())){
            String[] strs=this.pids.split(",");
            return Long.valueOf(strs[strs.length-1]);
        }
        return null;
    }

    @Override
    public void setChildren(List children) {
        this.children = children;
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

    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids;
    }

    public List<AuthPrivilege> getChildren() {
        return children;
    }

}
