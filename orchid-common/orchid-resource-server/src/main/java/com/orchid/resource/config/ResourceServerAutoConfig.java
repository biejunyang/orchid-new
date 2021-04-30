package com.orchid.resource.config;

import com.orchid.core.auth.AuthUser;
import com.orchid.core.condition.ResourceServerTokenCondition;
import com.orchid.resource.converter.SubjectAttributeUserTokenConverter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.*;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@EnableResourceServer
@Configuration
@Import({ResourceServerConfiguration.class, ResourceServerTokenServicesConfiguration.class})
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
public class ResourceServerAutoConfig {


    /**
     * 将Token数据（Map）转换成OAuth2AccessToken和OAuth2Authentication对象的转换器
     * Jwt Token解析，还有RemoteTokenService解析数据时会用到该类型转换器解析数据
     * RedisTokenStore和UserInfoTokenServices解析数据时用不到
     * @param userAuthenticationConverter
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAccessTokenConverter defaultAccessTokenConverter(UserAuthenticationConverter userAuthenticationConverter){
        DefaultAccessTokenConverter accessTokenConverter=new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
        return accessTokenConverter;
    }

    /**
     * 将Token数据（Map）转换成OAuth2Authentication对象的转换器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public UserAuthenticationConverter userAuthenticationConverter(){
        return new SubjectAttributeUserTokenConverter();
    }


    /**
     * 配置security.oauth2.resource.token-info-uri参数,进行远程Token校验
     */
    @Configuration
    @Conditional({ResourceServerTokenCondition.TokenInfoCondition.class})
    static class TokenInfoServicesConfiguration{


        private final ResourceServerProperties resource;

        public TokenInfoServicesConfiguration(ResourceServerProperties resource) {
            this.resource = resource;
        }

        /**
         * Token 存储在认证服务端内存中,需要进行远程token校验
         * @return
         */

        @Bean
        @Primary
        public RemoteTokenServices remoteTokenServices(AccessTokenConverter accessTokenConverter) {
            RemoteTokenServices services = new RemoteTokenServices();
            services.setCheckTokenEndpointUrl(resource.getTokenInfoUri());
            services.setClientId(this.resource.getClientId());
            services.setClientSecret(this.resource.getClientSecret());
            services.setAccessTokenConverter(accessTokenConverter);
            return services;
        }
    }




    /**
     * 配置security.oauth2.resource.user-info-uri参数,进行远程Token校验
     * ResourceServerTokenServicesConfiguration默认配置中已经配置了UserInfoTokenServices对象
     * 从远程端点user-info-uri中获取认证信息，并调用AuthoritiesExtractor和PrincipalExtractor对象从
     * 返回的结果信息中，解析出用户认证信息，注入这两个对象实现自定义Token数据解析
     */
    @Configuration
    @Conditional({ResourceServerTokenCondition.UserInfoCondition.class})
    static class UserInfoServicesConfiguration{

        public UserInfoServicesConfiguration() {
        }

        /**
         * 根据获取到的Token对应的数据信息，解析出权限数据
         * 认证服务器返回数据不同，解析方式不同
         * @return
         */
        @Bean
        @ConditionalOnMissingBean
        public AuthoritiesExtractor authoritiesExtractor(){
    //        return new FixedAuthoritiesExtractor();
            return map -> {
                Object authorities = map.get("authorities");

                List<GrantedAuthority> grantedAuthorities=null;

                if(authorities!=null && authorities instanceof List){
                    grantedAuthorities= AuthorityUtils.createAuthorityList(((List<String>)authorities).toArray(new String[]{}));
                }

                return grantedAuthorities;
            };
        }


        /**
         * 根据获取到的Token对应的数据信息，解析出用户数据
         * 认证服务器返回数据不同，解析方式不同
         * @return
         */
        @Bean
        @ConditionalOnMissingBean
        public PrincipalExtractor principalExtractor(){
            return map -> {
                AuthUser authUser=new AuthUser();
                authUser.setUsername(map.get("user_name").toString());
                Object authorities = map.get("authorities");

                List<GrantedAuthority> grantedAuthorities=null;

                if(authorities!=null && authorities instanceof List){
                    grantedAuthorities= AuthorityUtils.createAuthorityList(((List<String>)authorities).toArray(new String[]{}));
                    authUser.setAuthorities(grantedAuthorities);
                }
                Map<String, Object> addtionalInfomation=new HashMap<>(map);
                addtionalInfomation.remove("user_name");
                addtionalInfomation.remove("authorities");
                authUser.setAdditionalInformation(addtionalInfomation);
                return authUser;
            };
        }
    }


    @Configuration
    @Conditional({ResourceServerTokenCondition.JwtTokenCondition.class})
    static class JwtTokenServicesConfiguration{
        public JwtTokenServicesConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean
        JwtAccessTokenConverterConfigurer jwtAccessTokenConverterConfigurer(DefaultAccessTokenConverter defaultAccessTokenConverter){
            return jwtAccessTokenConverter -> {
                jwtAccessTokenConverter.setAccessTokenConverter(defaultAccessTokenConverter);
            };
        }

    }




    /**
     * 没有resource token相关参数时，默认使用DefaultTokenServices对象本地解析
     */
    @Configuration
    @Conditional({ResourceServerTokenCondition.NoTokenInfoCondition.class})
    static class NoTokenServicesConfiguration{

        public NoTokenServicesConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean
        @Primary
        public DefaultTokenServices defaultTokenServices(TokenStore tokenStore){
            DefaultTokenServices defaultTokenServices=new DefaultTokenServices();
            defaultTokenServices.setTokenStore(tokenStore);
            return defaultTokenServices;
        }


        /**
         * 从Redis中获取token数据并解析
         * @param redisConnectionFactory
         * @return
         */
        @Bean
        @Conditional(ResourceServerTokenCondition.RedisTokenInfoCondition.class)
        @ConditionalOnMissingBean
        public RedisTokenStore redisTokenStore(RedisConnectionFactory redisConnectionFactory){
            RedisTokenStore tokenStore=new RedisTokenStore(redisConnectionFactory);
            tokenStore.setPrefix("orchid:auth-server:");
            return tokenStore;
        }


        /**
         * 从内存中获取token数据并解析
         * @return
         */
        @Bean
        @Conditional(ResourceServerTokenCondition.InmemoryTokenInfoCondition.class)
        @ConditionalOnMissingBean
        public InMemoryTokenStore inMemoryTokenStore(){
            return new InMemoryTokenStore();
        }

    }

}
