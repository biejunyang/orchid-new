package com.orchid.web.init;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.orchid.core.context.sysconfig.SysConfigContext;
import com.orchid.core.context.sysconfig.SysConfigDetails;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 初始化系统配置参数监听器
 * <p>
 * 当spring装配好配置后，就去数据库读constants
 *
 * @author stylefeng
 * @date 2020/6/6 23:39
 */
public class SysConfigInitListener implements CommandLineRunner {


    @Override
    public void run(String... args) {
        ApplicationContext applicationContext=SpringUtil.getApplicationContext();

        Map<String, Object> sysConfigs=new HashMap<>();
        Map<String, SysConfigDetails> map=applicationContext.getBeansOfType(SysConfigDetails.class);
        if(CollectionUtil.isNotEmpty(map)){
            map.values().forEach(detail -> sysConfigs.putAll(detail.getSysConfigs()));
        }

        SysConfigContext.putConfigs(sysConfigs);
    }
}
