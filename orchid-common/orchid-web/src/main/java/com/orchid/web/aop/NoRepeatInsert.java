package com.orchid.web.aop;

import java.lang.annotation.*;

/**
 * @author biejunyang
 * @version 1.0
 * @date 2021/6/25 14:30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface NoRepeatInsert {

    //构建分布式锁的建名称
    String key() default "";

    //重复字段名称
    String label();

    //重复字段属性名称
    String name() default "";
}
