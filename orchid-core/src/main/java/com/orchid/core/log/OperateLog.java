package com.orchid.core.log;

import java.lang.annotation.*;

/**
 * @author biejunyang
 * @version 1.0
 * @date 2021/6/29 14:23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface OperateLog {

    //日志标题
    String title() default "";

    //功能模块名称(菜单名称)
    String name();

    //操作类型(如：添加，修改，删除)
    String type();

}
