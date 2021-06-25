package com.orchid.web.aop;

import cn.hutool.core.util.StrUtil;
import com.orchid.core.exception.ExceptionBuilder;
import com.orchid.core.util.SpelUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author biejunyang
 * @version 1.0
 * @date 2021/6/25 14:30
 */
public class NoRepeatInsertAop {

    private RedisTemplate<String, Object> redisTemplate;
    private static final String NO_REPEAT_INSERT_KEY_PREFIX = "NO_REPEAT_INSERT_LOCK";
    private static final int NO_REPEAT_INSERT_DURATION_SECOND = 3;


    public NoRepeatInsertAop(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Pointcut("@annotation(NoRepeatInsert)")
    public void pointCut() {
    }


    @Around("pointCut(noRepeatInsert)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, NoRepeatInsert noRepeatInsert) throws Throwable {
        //key：prefix:entity:column:value
//        String key = StrUtil.join(":","SysConfig","code", "xxxx");

        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Object target = proceedingJoinPoint.getTarget();
        Object[] arguments = proceedingJoinPoint.getArgs();
        String key = StrUtil.join(":", NO_REPEAT_INSERT_KEY_PREFIX, SpelUtil.parse(target,noRepeatInsert.key(), targetMethod, arguments));

        if(redisTemplate.opsForValue().setIfAbsent(key, System.currentTimeMillis(), NO_REPEAT_INSERT_DURATION_SECOND, TimeUnit.SECONDS)){
            try {
                return proceedingJoinPoint.proceed();
            }finally {
                redisTemplate.delete(key);
            }
        }else{
            throw ExceptionBuilder.build(5002, "重复插入");
        }
    }

}
