package com.orchid.core.log;

import org.aspectj.lang.JoinPoint;

/**
 * @author biejunyang
 * @version 1.0
 * @date 2021/6/29 14:27
 */
public interface OperateLogService {


    /**
     * 写入日志实现
     * @param operateLog
     * @param args
     * @param result
     * @param username
     */
    void insertLog(JoinPoint joinPoint, OperateLog operateLog);

}
