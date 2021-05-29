package com.orchid.core.context.thread;

import java.util.Map;

/**
 * 线程上问文
 * @author biejunyang
 * @version 1.0
 * @date 2021/5/18 9:59
 */
public class ThreadContext {

    private static final ThreadLocal<Map<String,Object>> THREAD_LOCAL= new ThreadLocal<>();


    /**
     * 保存当前线程数据
     * @param value
     */
    public static void set(Map<String,Object> value){
        THREAD_LOCAL.set(value);
    }


    /**
     * 获取当前上下文数据
     * @return
     */
    public static Map<String,Object> get(){
        return THREAD_LOCAL.get();
    }

    /**
     * 删除当前线程中的数据
     */
    public static void clear(){
        THREAD_LOCAL.remove();
    }
}
