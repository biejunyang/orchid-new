package com.orchid.web.config;

import com.orchid.web.filter.RequestThreadContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * web应用默认自动配置
 * @author biejunyang
 * @version 1.0
 * @date 2021/5/18 10:34
 */
@Configuration
public class WebAutoConfig {


    /**
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<RequestThreadContextFilter> requestThreadContextFilterFilterRegistrationBean() {
        FilterRegistrationBean<RequestThreadContextFilter> registration = new FilterRegistrationBean<>(new RequestThreadContextFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

}
