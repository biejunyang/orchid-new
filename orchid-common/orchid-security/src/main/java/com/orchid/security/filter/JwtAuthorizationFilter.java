package com.orchid.security.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.orchid.core.ResultCodeEnum;
import com.orchid.core.util.JwtTokenUtil;
import com.orchid.core.exception.JwtTokenException;
import com.orchid.core.Result;
import com.orchid.security.config.properties.JwtConfigProperties;
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
            String token = getToken(request);
            if (StrUtil.isNotEmpty(token)) {
                try {
                    String username = JwtTokenUtil.parseSubject(token);
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    doFilter(request, response, filterChain);
                    return;
                } catch (JwtTokenException e) {
                    ResponseUtil.renderJson(response, Result.error(e.code(), e.msg()));
                    return;
                }
            } else {
                ResponseUtil.renderJson(response, Result.error(ResultCodeEnum.NOT_LOGIN_ERROR));
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

        //忽略登录认证
        AntPathRequestMatcher loginMatcher = new AntPathRequestMatcher(jwtConfigProperties.getLogin(), HttpMethod.POST.name());
        if(loginMatcher.matches(request)){
            return true;
        }

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


    private String getToken(HttpServletRequest request){
        String token=request.getParameter("token");
        if(StrUtil.isNotEmpty(token)){
            return token;
        }
        token = request.getHeader("Authorization");
        if(StrUtil.isNotEmpty(token) && token.startsWith("Bearer ")){
            token = token.substring(7);
            return token;
        }
        return null;
    }
}
