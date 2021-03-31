package com.orchid.security.config;

import com.orchid.core.util.JwtTokenUtil;
import com.orchid.security.config.properties.JwtConfigProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@AutoConfigureBefore({SecurityAutoConfiguration.class})
@EnableConfigurationProperties({JwtConfigProperties.class})
@ConditionalOnClass({JwtTokenUtil.class})
@ConditionalOnWebApplication(
        type = ConditionalOnWebApplication.Type.SERVLET
)
@Import({JwtWebSecurityConfig.class})
public class JwtAutoConfig {



}
