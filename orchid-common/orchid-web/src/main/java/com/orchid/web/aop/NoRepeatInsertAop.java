package com.orchid.web.aop;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.orchid.core.ResultCode;
import com.orchid.core.ResultCodeEnum;
import com.orchid.core.exception.ExceptionBuilder;
import com.orchid.core.util.ClassUtils;
import com.orchid.core.util.SpelUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author biejunyang
 * @version 1.0
 * @date 2021/6/25 14:30
 */
@Aspect
@Slf4j
public class NoRepeatInsertAop {

    private RedisTemplate<String, Object> redisTemplate;
    private static final String NO_REPEAT_INSERT_KEY_PREFIX = "NO_REPEAT_INSERT_LOCK";
    private static final int NO_REPEAT_INSERT_DURATION_SECOND = 3;


    public NoRepeatInsertAop(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }



    @Around("@annotation(noRepeatInsert)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, NoRepeatInsert noRepeatInsert) throws Throwable {
        //key：prefix:entity:column:value,如：NO_REPEAT_INSERT_LOCK:SysConfig:code:value
//        String key = StrUtil.join(":","SysConfig","code", "xxxx");

        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Object target = proceedingJoinPoint.getTarget();
        Object[] arguments = proceedingJoinPoint.getArgs();
        String key = null;
        String value = null;
        if(StrUtil.isNotEmpty(noRepeatInsert.key())){
            key = SpelUtil.parse(target,noRepeatInsert.key(), targetMethod, arguments);
        }else if(StrUtil.isNotEmpty(noRepeatInsert.name())){
            for (Object argument : arguments) {
                Field field = ReflectUtil.getField(argument.getClass(),noRepeatInsert.name());
                if(field!=null){
                    value = ReflectUtil.getFieldValue(argument, field).toString();
                    key = StrUtil.join(":", argument.getClass().getSimpleName(), field.getName(), value);
                    break;
                }
            }
        }
        if(StrUtil.isNotEmpty(key)){
            key = StrUtil.join(":", NO_REPEAT_INSERT_KEY_PREFIX, key);
        }else{
            throw ExceptionBuilder.build(ResultCodeEnum.SERVER_ERROR.code(), "NoRepeatInsert Annotation arguments error: not found key or name attribute");
        }
        if(redisTemplate.opsForValue().setIfAbsent(key, System.currentTimeMillis(), NO_REPEAT_INSERT_DURATION_SECOND, TimeUnit.SECONDS)){
            log.debug("{}获取到锁{}", Thread.currentThread().getId(), key);
            try {
                return proceedingJoinPoint.proceed();
            }finally {
                redisTemplate.delete(key);
                log.debug("{}获取释放锁{}", Thread.currentThread().getId(), key);
            }
        }else{
            log.debug("{}没有获取到锁{}", Thread.currentThread().getId(), key);
            //防止系统释放锁异常，并且锁自动失效的情况下，释放锁
            Object val = redisTemplate.opsForValue().get(key);
            if(val!=null){
                long expireTimeMillis = (Long)val+NO_REPEAT_INSERT_DURATION_SECOND*1000;
                if(new Date(expireTimeMillis).before(new Date())){
                    redisTemplate.delete(key);
                }
            }
            throw ExceptionBuilder.build(5002, StrUtil.isNotEmpty(value)
                    ? String.format("%s:%s正在写入,不能重复插入", noRepeatInsert.label(), value)
                    : String.format("%s重复插入", noRepeatInsert.label()));
        }
    }

}
