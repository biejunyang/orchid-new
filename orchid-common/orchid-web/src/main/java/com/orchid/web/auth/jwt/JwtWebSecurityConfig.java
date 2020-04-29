package com.orchid.web.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConditionalOnMissingBean({WebSecurityConfigurerAdapter.class})
public class JwtWebSecurityConfig extends WebSecurityConfigurerAdapter{


    @Autowired
    private JwtConfigProperties jwtConfigProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JwtAuthorizationFilter jwtAuthorizationFilter=new JwtAuthorizationFilter(jwtConfigProperties);
        JwtAuthenticationFilter jwtAuthenticationFilter=new JwtAuthenticationFilter(jwtConfigProperties);
        jwtAuthenticationFilter.setAuthenticationManager(super.authenticationManagerBean());
        http
            .authorizeRequests()
                .anyRequest().authenticated().and()
            .formLogin().disable()
            .sessionManagement().disable()
            .csrf().disable()
            .addFilterAt(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterAt(jwtAuthenticationFilter, JwtAuthorizationFilter.class);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        WebSecurity and = web.ignoring().and();

        // 忽略 GET
        jwtConfigProperties.getIgnores().getGet().forEach(url -> and.ignoring().antMatchers(HttpMethod.GET, url));

        // 忽略 POST
        jwtConfigProperties.getIgnores().getPost().forEach(url -> and.ignoring().antMatchers(HttpMethod.POST, url));

        // 忽略 DELETE
        jwtConfigProperties.getIgnores().getDelete().forEach(url -> and.ignoring().antMatchers(HttpMethod.DELETE, url));

        // 忽略 PUT
        jwtConfigProperties.getIgnores().getPut().forEach(url -> and.ignoring().antMatchers(HttpMethod.PUT, url));

        // 忽略 HEAD
        jwtConfigProperties.getIgnores().getHead().forEach(url -> and.ignoring().antMatchers(HttpMethod.HEAD, url));

        // 忽略 PATCH
        jwtConfigProperties.getIgnores().getPatch().forEach(url -> and.ignoring().antMatchers(HttpMethod.PATCH, url));

        // 忽略 OPTIONS
        jwtConfigProperties.getIgnores().getOptions().forEach(url -> and.ignoring().antMatchers(HttpMethod.OPTIONS, url));

        // 忽略 TRACE
        jwtConfigProperties.getIgnores().getTrace().forEach(url -> and.ignoring().antMatchers(HttpMethod.TRACE, url));

        // 按照请求格式忽略
        jwtConfigProperties.getIgnores().getPattern().forEach(url -> and.ignoring().antMatchers(url));
    }
}
