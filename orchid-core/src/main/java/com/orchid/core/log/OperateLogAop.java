package com.orchid.core.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author biejunyang
 * @version 1.0
 * @date 2021/6/29 14:25
 */
@Aspect
public class OperateLogAop {

    private OperateLogService operateLogService;

    public OperateLogAop(OperateLogService operateLogService) {
        this.operateLogService = operateLogService;
    }


    /**
     * 日志切入点
     *
     * @author xuyuxiang
     * @date 2020/3/23 17:10
     */
    @Pointcut("@annotation(OperateLog)")
    private void logPointCut() {
    }


    /**
     * 操作成功返回结果记录日志
     *
     * @author xuyuxiang
     * @date 2020/3/20 11:51
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        OperateLog operateLog = method.getAnnotation(OperateLog.class);
        Object[] args = joinPoint.getArgs();
//        //异步记录日志
//        LogManager.me().executeOperationLog(
//                businessLog, LoginContextHolder.me().getSysLoginUserAccount(), joinPoint, JSON.toJSONString(result));
        operateLogService.insertLog(joinPoint, operateLog);
    }

    /**
     * 操作发生异常记录日志
     *
     * @author xuyuxiang
     * @date 2020/3/21 11:38
     */
    @AfterThrowing(pointcut = "logPointCut()", throwing = "exception")
    public void doAfterThrowing(JoinPoint joinPoint, Exception exception) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        OperateLog operateLog = method.getAnnotation(OperateLog.class);
        operateLogService.insertLog(joinPoint, operateLog);

        //异步记录日志
//        LogManager.me().executeExceptionLog(
//                businessLog, LoginContextHolder.me().getSysLoginUserAccount(), joinPoint, exception);
    }
}
