package com.orchid.web.filter;

import cn.hutool.core.util.StrUtil;
import com.orchid.core.Result;
import com.orchid.core.ResultCodeEnum;
import com.orchid.core.auth.AuthContextHolder;
import com.orchid.core.context.thread.ThreadContext;
import com.orchid.web.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 请求线程上线文过滤器
 * @author biejunyang
 * @version 1.0
 * @date 2021/5/18 10:31
 */
@Slf4j
public class RequestThreadContextFilter extends OncePerRequestFilter {

    private static final String NO_REPEAT_COMMIT_KEY_PREFIX = "NO_REPEAT_COMMIT_LOCK";
    private static final int NO_REPEAT_COMMIT_DURATION_SECOND = 120;//锁失效时间

    private RedisTemplate<String, Object> redisTemplate;

    public RequestThreadContextFilter(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //构建防止重复提交的分布式锁名称：prefix:username:requestname:
        String username = AuthContextHolder.getContext().getUsername();
        String requestName = httpServletRequest.getRequestURI();
        String key = StrUtil.join(":",NO_REPEAT_COMMIT_KEY_PREFIX, username, requestName);

        //请求返回之前该用户不能重复提交
        if(StrUtil.isNotEmpty(username) && redisTemplate.opsForValue().setIfAbsent(key, System.currentTimeMillis(), NO_REPEAT_COMMIT_DURATION_SECOND, TimeUnit.SECONDS)){
            try {
                log.debug("{}获取到执行请求{}的锁", username, requestName);

//                 //生成唯一请求号uuid
//                String requestNo = UUID.randomUUID().toString();
//
//                // 增加响应头的请求号
//                httpServletResponse.addHeader(REQUEST_NO_HEADER_NAME, requestNo);
//
//                // 临时存储
//                RequestNoContext.set(requestNo);
                filterChain.doFilter(httpServletRequest, httpServletResponse);

            }finally {
                redisTemplate.delete(key);
                log.debug("{}释放执行请求{}的锁", username, requestName);
            }

        }else{
            log.debug("{}重复提交请求{}", username, requestName);

            //防止系统释放锁异常，并且锁自动失效的情况下，释放锁
            Object val = redisTemplate.opsForValue().get(key);
            if(val!=null){
                long expireTimeMillis = (Long)val+NO_REPEAT_COMMIT_DURATION_SECOND*1000;
                if(new Date(expireTimeMillis).before(new Date())){
                    log.debug("{}执行请求{}的锁过期删除", username, requestName);
                    redisTemplate.delete(key);
                }
            }
            ResponseUtil.renderJson(httpServletResponse, Result.error(ResultCodeEnum.REPEAT_COMMIT_ERROR));
        }

    }
}
