package com.orchid.web.auth.jwt;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.orchid.core.http.R;
import com.orchid.core.jwt.JwtTokenUtil;
import com.orchid.core.jwt.exception.JwtTokenException;
import com.orchid.web.util.ResponseUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

/**
 * JWT鉴权过滤器
 */
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {


    private JwtConfigProperties jwtConfigProperties;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (checkIgnores(request)) {
            filterChain.doFilter(request, response);
            return;
        }else{
            String tokenHeader = request.getHeader("Authorization");
            if (ObjectUtil.isNotEmpty(tokenHeader)) {
                if (tokenHeader.startsWith("Bearer ")) {
                    String token = tokenHeader.substring(7);
                    try {
                        String username = JwtTokenUtil.parseSubject(token);
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        doFilter(request, response, filterChain);
                        return;
                    } catch (JwtTokenException e) {
                        ResponseUtil.renderJson(response, R.error(e.getMessage()));
                        return;
                    }
                } else {
                    ResponseUtil.renderJson(response, R.error("invalid token", null));
                }
            } else {
                ResponseUtil.renderJson(response, R.error("未登录"));
            }
        }
    }


    /**
     * 请求是否不需要进行权限拦截
     *
     * @param request 当前请求
     * @return true - 忽略，false - 不忽略
     */
    private boolean checkIgnores(HttpServletRequest request) {
        String method = request.getMethod();

        HttpMethod httpMethod = HttpMethod.resolve(method);
        if (ObjectUtil.isNull(httpMethod)) {
            httpMethod = HttpMethod.GET;
        }

        Set<String> ignores = CollectionUtil.newHashSet();

        switch (httpMethod) {
            case GET:
                ignores.addAll(jwtConfigProperties.getIgnores()
                        .getGet());
                break;
            case PUT:
                ignores.addAll(jwtConfigProperties.getIgnores()
                        .getPut());
                break;
            case HEAD:
                ignores.addAll(jwtConfigProperties.getIgnores()
                        .getHead());
                break;
            case POST:
                ignores.addAll(jwtConfigProperties.getIgnores()
                        .getPost());
                break;
            case PATCH:
                ignores.addAll(jwtConfigProperties.getIgnores()
                        .getPatch());
                break;
            case TRACE:
                ignores.addAll(jwtConfigProperties.getIgnores()
                        .getTrace());
                break;
            case DELETE:
                ignores.addAll(jwtConfigProperties.getIgnores()
                        .getDelete());
                break;
            case OPTIONS:
                ignores.addAll(jwtConfigProperties.getIgnores()
                        .getOptions());
                break;
            default:
                break;
        }

        ignores.addAll(jwtConfigProperties.getIgnores()
                .getPattern());

        if (CollectionUtil.isNotEmpty(ignores)) {
            for (String ignore : ignores) {
                AntPathRequestMatcher matcher = new AntPathRequestMatcher(ignore, method);
                if (matcher.matches(request)) {
                    return true;
                }
            }
        }

        return false;
    }
}
