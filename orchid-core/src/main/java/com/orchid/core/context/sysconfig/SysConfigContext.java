package com.orchid.core.context.sysconfig;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统参数配置上下文
 * @author biejunyang
 * @version 1.0
 * @date 2021/5/17 16:32
 */
public class SysConfigContext {
    /**
     * 所有的常量，可以增删改查
     */
    private static Map<String, Object> SYS_CONFIG_HOLDER = new ConcurrentHashMap<>();

    /**
     * 添加系统配置参数
     */
    public static void putConfig(String key, Object value) {

        if (ObjectUtil.hasEmpty(key, value)) {
            return;
        }
        SYS_CONFIG_HOLDER.put(key, value);
    }

    /**
     * 添加系统配置参数
     */
    public static void putConfigs(Map<String,Object> constants) {

        if (CollectionUtil.isEmpty(constants)) {
            return;
        }
        SYS_CONFIG_HOLDER.putAll(constants);
    }

    /**
     * 删除系统配置参数
     */
    public static void deleteConfig(String key) {
        if (ObjectUtil.hasEmpty(key)) {
            return;
        }

        SYS_CONFIG_HOLDER.remove(key);
    }


    /**
     * 获取系统配置参数
     * @param configCode
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getConfig(String configCode, Class<T> clazz){
        Object configValue = SYS_CONFIG_HOLDER.get(configCode);
        return configValue!=null ? Convert.convert(clazz, configValue):null;
    }

}
