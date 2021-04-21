package com.orchid.resource.config;

import com.orchid.core.condition.TokenTypeCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * OAuth2 Token Manage Config
 */
@Configuration
public class TokenConfig {


    /**
     * Token信息存储在分布式缓存Redis中时
     */
    @Configuration
    @Conditional({TokenTypeCondition.RedisTokenCondition.class})
    protected class RedisTokenConfig {


        @Bean
        @ConditionalOnMissingBean(TokenStore.class)
        public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory){
            RedisTokenStore tokenStore=new RedisTokenStore(redisConnectionFactory);
            tokenStore.setPrefix("orchid:auth-server:");
            return tokenStore;
        }

    }

}
