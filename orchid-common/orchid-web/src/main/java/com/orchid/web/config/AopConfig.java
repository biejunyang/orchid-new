package com.orchid.web.config;

import com.orchid.core.log.OperateLogAop;
import com.orchid.core.log.OperateLogService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author biejunyang
 * @version 1.0
 * @date 2021/5/18 15:17
 */
@Configuration
public class AopConfig {

    @Primary
    @Bean
    public OperateLogAop operateLogAop(OperateLogService operateLogService){
        return new OperateLogAop(operateLogService);
    }

}
