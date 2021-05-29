package com.orchid.web.init;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.orchid.core.context.CacheDetails;
import com.orchid.core.context.sysconfig.SysConfigContext;
import com.orchid.core.context.sysconfig.SysConfigDetails;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author biejunyang
 * @version 1.0
 * @date 2021/5/18 11:19
 */
public class CacheContextInitListener implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
//        ApplicationContext applicationContext= SpringUtil.getApplicationContext();
//
//        Map<String, Object> sysConfigs=new HashMap<>();
//        Map<String, CacheDetails> map=applicationContext.getBeansOfType(CacheDetails.class);
//        if(CollectionUtil.isNotEmpty(map)){
//            map.values().forEach(detail -> sysConfigs.putAll(detail.getSysConfigs()));
//        }
//
//        SysConfigContext.putConfigs(sysConfigs);
    }
}
