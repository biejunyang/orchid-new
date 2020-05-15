package com.orchid.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

public class DefaultMetaObjectHandler implements MetaObjectHandler {

    private final String createTime = "createTime";
    private final String updateTime = "updateTime";
    private final String disabled = "disabled";

    @Override
    public void insertFill(MetaObject metaObject) {
        if (null == metaObject.getValue(createTime)) {
            Date date=new Date();
            System.out.println(date);
            this.fillStrategy(metaObject, createTime, date);
        }
        if (null == metaObject.getValue(disabled)) {
            this.fillStrategy(metaObject, disabled, 0);
        }
    }


    @Override
    public void updateFill(MetaObject metaObject) {
        if (null == metaObject.getValue(updateTime)) {
            this.fillStrategy(metaObject, createTime, LocalDateTime.now());
        }
    }
}
