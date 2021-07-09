package com.orchid.core.log;

import org.aspectj.lang.JoinPoint;

/**
 * @author biejunyang
 * @version 1.0
 * @date 2021/6/29 14:27
 */
public interface OperateLogService {


    /**
     * 操作日志写入实现
     * @param operateLog
     * @param joinPoint
     * @param result
     */
    void insertOperateLog(OperateLog operateLog, JoinPoint joinPoint, Object result);


    /**
     * 异常操作日志写入实现
     * @param operateLog
     * @param joinPoint
     * @param exception
     */
    void insertExceptionLog(OperateLog operateLog, JoinPoint joinPoint, Exception exception);
}
