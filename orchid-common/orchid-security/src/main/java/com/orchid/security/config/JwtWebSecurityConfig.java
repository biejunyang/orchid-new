package com.orchid.security.config;

import com.orchid.security.config.properties.JwtConfigProperties;
import com.orchid.security.filter.JwtAuthenticationFilter;
import com.orchid.security.filter.JwtAuthorizationFilter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConditionalOnMissingBean({WebSecurityConfigurerAdapter.class})
public class JwtWebSecurityConfig extends WebSecurityConfigurerAdapter{


    @Autowired
    private JwtConfigProperties jwtConfigProperties;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JwtAuthorizationFilter jwtAuthorizationFilter=new JwtAuthorizationFilter(jwtConfigProperties);
        JwtAuthenticationFilter jwtAuthenticationFilter=new JwtAuthenticationFilter(jwtConfigProperties);
        jwtAuthenticationFilter.setAuthenticationManager(super.authenticationManagerBean());
        http
            .authorizeRequests()
                .antMatchers(jwtConfigProperties.getLogin()).permitAll()
                .anyRequest().authenticated().and()
            .formLogin().disable()
            .sessionManagement().disable()
            .csrf().disable()
            .addFilterAt(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterAt(jwtAuthenticationFilter, JwtAuthorizationFilter.class);
//            .userDetailsService(new UserDetailsService() {
//                @Override
//                public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                    return null;
//                }
//            });
    }



    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /**
     * 不需要认证的请求配置
     * @param web
     * @throws Exception
     */
//    @Override
//    public void configure(WebSecurity web) throws Exception {
        //忽略登录认证请求
//        WebSecurity and = web.ignoring().antMatchers(HttpMethod.POST, jwtConfigProperties.getLogin()).and();
//
//
//        // 忽略 GET
//        jwtConfigProperties.getIgnores().getGet().forEach(url -> and.ignoring().antMatchers(HttpMethod.GET, url));
//
//        // 忽略 POST
//        jwtConfigProperties.getIgnores().getPost().forEach(url -> and.ignoring().antMatchers(HttpMethod.POST, url));
//
//        // 忽略 DELETE
//        jwtConfigProperties.getIgnores().getDelete().forEach(url -> and.ignoring().antMatchers(HttpMethod.DELETE, url));
//
//        // 忽略 PUT
//        jwtConfigProperties.getIgnores().getPut().forEach(url -> and.ignoring().antMatchers(HttpMethod.PUT, url));
//
//        // 忽略 HEAD
//        jwtConfigProperties.getIgnores().getHead().forEach(url -> and.ignoring().antMatchers(HttpMethod.HEAD, url));
//
//        // 忽略 PATCH
//        jwtConfigProperties.getIgnores().getPatch().forEach(url -> and.ignoring().antMatchers(HttpMethod.PATCH, url));
//
//        // 忽略 OPTIONS
//        jwtConfigProperties.getIgnores().getOptions().forEach(url -> and.ignoring().antMatchers(HttpMethod.OPTIONS, url));
//
//        // 忽略 TRACE
//        jwtConfigProperties.getIgnores().getTrace().forEach(url -> and.ignoring().antMatchers(HttpMethod.TRACE, url));
//
//        // 按照请求格式忽略
//        jwtConfigProperties.getIgnores().getPattern().forEach(url -> and.ignoring().antMatchers(url));
//    }
}
