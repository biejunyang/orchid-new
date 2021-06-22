package com.orchid.mybatis.handler;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.orchid.core.auth.AuthContext;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

public class DefaultMetaObjectHandler implements MetaObjectHandler {

    private final String createTime = "createTime";
    private final String createUser = "createUser";
    private final String createClient = "createClient";

    private final String updateUser = "updateUser";
    private final String updateClient = "updateClient";
    private final String updateTime = "updateTime";

    private final String disabled = "disabled";

    private AuthContext authContext;

    public DefaultMetaObjectHandler(AuthContext authContext) {
        this.authContext = authContext;
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        if (null == metaObject.getValue(createTime)) {
            this.strictInsertFill(metaObject, createTime, Date.class, new Date());
        }
        if(ObjectUtil.isEmpty(metaObject.getValue(createUser)) && authContext!=null){
            this.strictInsertFill(metaObject, createUser, String.class, authContext.getUsername());
        }
        if (null == metaObject.getValue(createClient)) {
            this.strictInsertFill(metaObject, createClient, String.class, authContext.getClientId());
        }
        if (null == metaObject.getValue(disabled)) {
            this.strictInsertFill(metaObject, disabled, Integer.class, 0);
        }

    }


    @Override
    public void updateFill(MetaObject metaObject) {
        if (null == metaObject.getValue(updateTime)) {
            this.strictUpdateFill(metaObject, updateTime, Date.class, new Date());
        }
        if (ObjectUtil.isEmpty(metaObject.getValue(updateUser)) && authContext != null) {
            this.strictUpdateFill(metaObject, updateUser, String.class, authContext.getUsername());
        }
        if (null == metaObject.getValue(updateClient)) {
            this.strictUpdateFill(metaObject, updateClient, String.class, authContext.getClientId());
        }
    }


    public AuthContext getAuthContext() {
        return authContext;
    }

    public void setAuthContext(AuthContext authContext) {
        this.authContext = authContext;
    }
}
