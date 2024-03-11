package com.demo.library.aspect.exception.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Aspect
@Component
public class ForRecord {
    @Pointcut("execution(* com.demo.library.service.impl.RecordServiceImpl.borrowBook(..))")
    public void borrowBookPointcut() {
    }

    @Around("borrowBookPointcut()")
    public Integer handleExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return (Integer) joinPoint.proceed();
        } catch(IndexOutOfBoundsException e) {
            throw new RuntimeException("No such book.");
        }
    }

}
