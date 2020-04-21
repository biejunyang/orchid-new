package com.orchid.web.auth;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtAutoConfig {

    @ConditionalOnProperty(name = "orchid.auth.jwt.enable", havingValue = "true")
    @Bean
    public JwtTokenUtil jwtTokenUtil(){
        return new JwtTokenUtil();
    };


}
