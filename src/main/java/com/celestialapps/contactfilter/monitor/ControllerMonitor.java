package com.celestialapps.contactfilter.monitor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;


@Aspect
@Component
public class ControllerMonitor {

    private final Logger logger = LoggerFactory.getLogger(ControllerMonitor.class);
    private long startTime;

    @Before("execution(* com.celestialapps.contactfilter.controller.*Controller.*(..))")
    public void logBeforeMethodCall(JoinPoint joinPoint) {
        startTime = System.currentTimeMillis();
    }

    @AfterReturning("execution(* com.celestialapps.contactfilter.controller.*Controller.*(..))")
    public void logAfterReturning(JoinPoint joinPoint) {
        long endTime = System.currentTimeMillis() - startTime;

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        String before = "Method Name: " + method.getName() + ", Args: " + Arrays.toString(joinPoint.getArgs());
        logger.info(before + ", Speed of Execution: " + endTime + " milliseconds" + "(" + endTime/1000.0 + " - seconds)");
    }

    @AfterThrowing("execution(* com.celestialapps.contactfilter.controller.*Controller.*(..))")
    public void logException(JoinPoint joinPoint) {
        System.out.println("Возникла ошибка в " + joinPoint);
    }



}

