package com.orchid.web.aop;

/**
 * @author biejunyang
 * @version 1.0
 * @date 2021/6/25 14:30
 */
public @interface NoRepeatInsert {

    String key();

    String name() default "";

    String value() default "";
}
