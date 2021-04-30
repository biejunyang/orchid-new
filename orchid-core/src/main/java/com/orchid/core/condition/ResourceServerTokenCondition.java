package com.orchid.core.condition;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

/**
 * @author biejunyang
 * @version 1.0
 * @date 2021/4/28 14:50
 */
public class ResourceServerTokenCondition {


    /**
     * 配置了security.oauth2.resource.user-info-uri参数
     */
    public static class UserInfoCondition extends SpringBootCondition {

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ConditionMessage.Builder message = ConditionMessage.forCondition("OAuth UserInfo Condition", new Object[0]);
            Environment environment = context.getEnvironment();
            String userInfoUri = environment.getProperty("security.oauth2.resource.user-info-uri");

            return StringUtils.hasLength(userInfoUri) ? ConditionOutcome.match(message.foundExactly("found user-info-uri property")) : ConditionOutcome.noMatch(message.didNotFind("user info").atAll());
        }
    }



    /**
     * 配置了security.oauth2.resource.token-info-uri参数
     */
    public static class TokenInfoCondition extends SpringBootCondition {

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {

            ConditionMessage.Builder message = ConditionMessage.forCondition("OAuth TokenInfo Condition", new Object[0]);
            Environment environment = context.getEnvironment();
            String tokenInfoUri = environment.getProperty("security.oauth2.resource.token-info-uri");

            return StringUtils.hasLength(tokenInfoUri) ? ConditionOutcome.match(message.foundExactly("found token-info-uri property")) : ConditionOutcome.noMatch(message.didNotFind("token info").atAll());
        }
    }


    /**
     * 配置了security.oauth2.resource.jwk.key-set-uri参数
     */
    public static class JwkCondition extends SpringBootCondition {

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ConditionMessage.Builder message = ConditionMessage.forCondition("OAuth JWK Condition", new Object[0]);
            Environment environment = context.getEnvironment();
            String keyUri = environment.getProperty("security.oauth2.resource.jwk.key-set-uri");
            return StringUtils.hasText(keyUri) ? ConditionOutcome.match(message.foundExactly("provided jwk key set URI")) : ConditionOutcome.noMatch(message.didNotFind("key jwk set URI not provided").atAll());
        }
    }


    /**
     * 配置了security.oauth2.resource.jwt.key-value或security.oauth2.resource.jwt.key-uri
     */
    public static class JwtTokenCondition extends SpringBootCondition {

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ConditionMessage.Builder message = ConditionMessage.forCondition("OAuth JWT Condition", new Object[0]);
            Environment environment = context.getEnvironment();
            String keyValue = environment.getProperty("security.oauth2.resource.jwt.key-value");
            String keyUri = environment.getProperty("security.oauth2.resource.jwt.key-uri");
            return !StringUtils.hasText(keyValue) && !StringUtils.hasText(keyUri) ? ConditionOutcome.noMatch(message.didNotFind("provided public key").atAll()) : ConditionOutcome.match(message.foundExactly("provided public key"));
        }
    }




    /**
     * 配置了security.oauth2.resource.jwt.key-store参数
     */
    public static class JwtKeyStoreCondition extends SpringBootCondition {
        private JwtKeyStoreCondition() {
        }

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ConditionMessage.Builder message = ConditionMessage.forCondition("OAuth JWT KeyStore Condition", new Object[0]);
            Environment environment = context.getEnvironment();
            String keyStore = environment.getProperty("security.oauth2.resource.jwt.key-store");
            return StringUtils.hasText(keyStore) ? ConditionOutcome.match(message.foundExactly("provided key store location")) : ConditionOutcome.noMatch(message.didNotFind("key store location not provided").atAll());
        }
    }


    /**
     * 资源服务没有配置上述token参数时
     */
    public static class NoTokenInfoCondition extends SpringBootCondition {
        private TokenInfoCondition tokenInfoCondition = new TokenInfoCondition();
        private UserInfoCondition userInfoCondition = new UserInfoCondition();
        private JwkCondition jwkCondition=new JwkCondition();
        private JwtTokenCondition jwtTokenCondition=new JwtTokenCondition();
        private JwtKeyStoreCondition jwtKeyStoreCondition=new JwtKeyStoreCondition();

        private NoTokenInfoCondition() {
        }

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ConditionMessage.Builder message = ConditionMessage.forCondition("OAuth NotTokenInfoCondition Condition", new Object[0]);

            boolean tokenInfo=tokenInfoCondition.getMatchOutcome(context, metadata).isMatch();
            boolean userInfo=userInfoCondition.getMatchOutcome(context, metadata).isMatch();
            boolean jwkInfo=jwkCondition.getMatchOutcome(context, metadata).isMatch();
            boolean jwtTokenInfo=jwtTokenCondition.getMatchOutcome(context, metadata).isMatch();
            boolean jwtKeyStoreInfo=jwtKeyStoreCondition.getMatchOutcome(context, metadata).isMatch();

            boolean result= tokenInfo | userInfo | jwkInfo | jwtTokenInfo | jwtKeyStoreInfo;

            return !result ? ConditionOutcome.match(message.foundExactly("no token info property")) : ConditionOutcome.noMatch(message.didNotFind("found token info").atAll());
        }
    }


    /**
     * 资源服务没有配置上述token参数，并且配置了redis环境参数时，定义为Redis Token存储
     */
    public static class RedisTokenInfoCondition extends SpringBootCondition {
        private NoTokenInfoCondition noTokenInfoCondition = new NoTokenInfoCondition();

        private RedisTokenInfoCondition() {
        }

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ConditionMessage.Builder message = ConditionMessage.forCondition("OAuth RedisTokenInfoCondition Condition", new Object[0]);

            boolean noTokenInfo=noTokenInfoCondition.getMatchOutcome(context, metadata).isMatch();

            Environment environment = context.getEnvironment();
            String redisHost = environment.getProperty("spring.redis.host");
            return noTokenInfo && StringUtils.hasText(redisHost) ? ConditionOutcome.match(message.foundExactly("redis token info property")) : ConditionOutcome.noMatch(message.didNotFind("found reids info").atAll());
        }
    }

    /**
     * 资源服务没有配置上述token参数，并且没有配置了redis环境参数时，定义为内存Token存储
     */
    public static class InmemoryTokenInfoCondition extends SpringBootCondition {
        private NoTokenInfoCondition noTokenInfoCondition = new NoTokenInfoCondition();

        private InmemoryTokenInfoCondition() {
        }

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ConditionMessage.Builder message = ConditionMessage.forCondition("OAuth RedisTokenInfoCondition Condition", new Object[0]);

            boolean noTokenInfo=noTokenInfoCondition.getMatchOutcome(context, metadata).isMatch();

            Environment environment = context.getEnvironment();
            String redisHost = environment.getProperty("spring.redis.host");
            return noTokenInfo && StringUtils.isEmpty(redisHost) ? ConditionOutcome.match(message.foundExactly("inmemory token info property")) : ConditionOutcome.noMatch(message.didNotFind("found token and redis info").atAll());
        }
    }

}
