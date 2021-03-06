package com.orchid.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseEntity<T extends BaseEntity<T>> extends Model<T> {

    /**
     * serialVersionUID
     */
    protected static final long serialVersionUID = 1L;
    /**
     * ID
     */
    protected Long id;


    /**
     * 禁用标识 （0：启用；1：禁用；）
     */
    @TableField(fill = FieldFill.INSERT)
    protected Integer disabled;

    /**
     * 创建人名称
     */
    @TableField(fill = FieldFill.INSERT)
    protected String createUser;

    /**
     * 创建应用
     */
    @TableField(fill = FieldFill.INSERT)
    protected String createClient;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    protected Date createTime;

    /**
     * 更新人名称
     */
    @TableField(fill = FieldFill.UPDATE)
    protected String updateUser;

    /**
     * 更新应用
     */
    @TableField(fill = FieldFill.UPDATE)
    protected String updateClient;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    protected Date updateTime;



    public Long getId() {
        return id;
    }

    public T setId(Long id) {
        this.id = id;
        return (T)this;
    }


    public Integer getDisabled() {
        return disabled;
    }

    public T setDisabled(Integer disabled) {
        this.disabled = disabled;
        return (T)this;
    }

    public String getCreateUser() {
        return createUser;
    }

    public T setCreateUser(String createUser) {
        this.createUser = createUser;
        return (T)this;
    }

    public String getCreateClient() {
        return createClient;
    }

    public T setCreateClient(String createClient) {
        this.createClient = createClient;
        return (T)this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public T setCreateTime(Date createTime) {
        this.createTime = createTime;
        return (T)this;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public T setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
        return (T)this;
    }

    public String getUpdateClient() {
        return updateClient;
    }

    public T setUpdateClient(String updateClient) {
        this.updateClient = updateClient;
        return (T)this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public T setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return (T)this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
