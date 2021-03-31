package com.orchid.security.filter;

import com.orchid.core.exception.ExceptionBuilder;
import com.orchid.core.util.JwtTokenUtil;
import com.orchid.core.Result;
import com.orchid.security.config.properties.JwtConfigProperties;
import com.orchid.web.util.ResponseUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private JwtConfigProperties jwtConfigProperties;

    public JwtAuthenticationFilter(JwtConfigProperties jwtConfigProperties) {
        super(jwtConfigProperties.getLogin());
        this.jwtConfigProperties=jwtConfigProperties;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String username = request.getParameter(jwtConfigProperties.getUsernameParam());
        String password = request.getParameter(jwtConfigProperties.getUsernameParam());
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(authRequest);
    }


    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {

        String username = authResult.getPrincipal().toString();

        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();

        Map<String, Object> map=new HashMap<>();
        map.put(username, username);
        map.put("authorities", authorities);
        String token=JwtTokenUtil.createToken(username,map);
        ResponseUtil.renderJson(response, Result.success(token));

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        ResponseUtil.renderJson(response, Result.error(failed.getMessage()));
    }
}
