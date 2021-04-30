package com.orchid.resource.config;

import com.orchid.core.auth.AuthContext;
import com.orchid.resource.context.ResourceServerAuthContextImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author biejunyang
 * @version 1.0
 * @date 2021/4/25 17:04
 */
@Configuration
public class ResourceServerAuthContextConfig {


    @ConditionalOnMissingBean
    @Bean
    AuthContext authContext(){
        return new ResourceServerAuthContextImpl();
    }
}
