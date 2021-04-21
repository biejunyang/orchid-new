package com.orchid.core.condition;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

public class TokenTypeCondition {


    public static class JwtTokenCondition extends SpringBootCondition {

        private JwtTokenCondition() {
        }

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {

            ConditionMessage.Builder message = ConditionMessage.forCondition("Jwt Token Store Condition", new Object[0]);
            Environment environment = context.getEnvironment();
            String tokenStoreType = environment.getProperty("orchid.auth-server.token-store-type");
            return StringUtils.hasText(tokenStoreType) && tokenStoreType.equals("jwt") ? ConditionOutcome.match(message.foundExactly("provided jwk token store")) : ConditionOutcome.noMatch(message.didNotFind("jwt token store not provided").atAll());

        }
    }

    public static class RedisTokenCondition extends SpringBootCondition {

        private RedisTokenCondition() {
        }

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {

            ConditionMessage.Builder message = ConditionMessage.forCondition("Redis Token Store Condition", new Object[0]);
            Environment environment = context.getEnvironment();
            String tokenStoreType = environment.getProperty("orchid.auth-server.token-store-type");

            return StringUtils.hasText(tokenStoreType) && tokenStoreType.equals("redis") ? ConditionOutcome.match(message.foundExactly("provided redis token store")) : ConditionOutcome.noMatch(message.didNotFind("redis token store not provided").atAll());
        }
    }

    public static class InMemoryTokenCondition extends SpringBootCondition {

        private InMemoryTokenCondition() {
        }

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {

            ConditionMessage.Builder message = ConditionMessage.forCondition("Inmemory Token Store Condition", new Object[0]);
            Environment environment = context.getEnvironment();
            String tokenStoreType = environment.getProperty("orchid.auth-server.token-store-type");

            return StringUtils.hasText(tokenStoreType) && tokenStoreType.equals("inmemory") ? ConditionOutcome.match(message.foundExactly("provided inmemory token store")) : ConditionOutcome.noMatch(message.didNotFind("inmemory token store not provided").atAll());
        }
    }
}
