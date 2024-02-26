package com.demo.library.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(* com.demo.library.service.*.*(..))")
    public void serviceLayerPointcut() {
    }

    @Before("serviceLayerPointcut()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Executing:{}", joinPoint.getSignature());
    }

    @AfterReturning(
            pointcut = "serviceLayerPointcut()",
            returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Method executed successfully: {}", joinPoint.getSignature());
        logger.info("Result: {}", result);
    }

    @AfterThrowing(
            pointcut = "serviceLayerPointcut()",
            throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        logger.error("Method execution failed: {}", joinPoint.getSignature(), e);
    }

    @Around("serviceLayerPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            logger.info("Method executed in: {} ms", elapsedTime);
            return result;
        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument: {}", joinPoint.getArgs());
            throw e;
        }
    }
}
