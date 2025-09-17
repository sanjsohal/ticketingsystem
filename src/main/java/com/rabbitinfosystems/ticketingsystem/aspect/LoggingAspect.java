package com.rabbitinfosystems.ticketingsystem.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.rabbitinfosystems.ticketingsystem.controller..*(..)")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("Entering method: {}", methodName);
        try {
            Object result = joinPoint.proceed();
            log.info("Exiting method: {}", methodName);
            return result;
        } catch (Throwable throwable) {
            log.error("Exception in method: {}", methodName, throwable);
            throw throwable;
        }
    }
}
