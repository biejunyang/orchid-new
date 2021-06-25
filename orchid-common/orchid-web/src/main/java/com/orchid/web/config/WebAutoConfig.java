package com.orchid.web.config;

import com.orchid.web.aop.NoRepeatInsertAop;
import com.orchid.web.filter.RequestThreadContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * web应用默认自动配置
 * @author biejunyang
 * @version 1.0
 * @date 2021/5/18 10:34
 */
@Configuration
public class WebAutoConfig {


    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 注册请求线程上下文过滤器
     * @return
     */
//    @Bean
//    public FilterRegistrationBean<RequestThreadContextFilter> requestThreadContextFilterFilterRegistrationBean(RedisTemplate redisTemplate) {
//        FilterRegistrationBean<RequestThreadContextFilter> registration = new FilterRegistrationBean<>(new RequestThreadContextFilter(redisTemplate));
//        registration.addUrlPatterns("/*");
//        return registration;
//    }



    @Bean
    public NoRepeatInsertAop noRepeatInsertAop(){
        return new NoRepeatInsertAop(redisTemplate);
    }

}
