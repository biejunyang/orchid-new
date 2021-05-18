package com.orchid.web.filter;

import com.orchid.core.context.thread.ThreadContext;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author biejunyang
 * @version 1.0
 * @date 2021/5/18 10:31
 */
public class RequestThreadContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
//            // 生成唯一请求号uuid
//            String requestNo = UUID.randomUUID().toString();
//
//            // 增加响应头的请求号
//            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//            httpServletResponse.addHeader(REQUEST_NO_HEADER_NAME, requestNo);
//
//            // 临时存储
//            RequestNoContext.set(requestNo);


            filterChain.doFilter(httpServletRequest, httpServletResponse);

        } finally {
            // 清除临时存储的唯一编号
            ThreadContext.clear();
        }
    }
}
