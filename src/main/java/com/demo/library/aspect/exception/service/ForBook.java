package com.demo.library.aspect.exception.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

import java.util.NoSuchElementException;

public class ForBook {

    @Pointcut("execution(* com.demo.library.service.impl.BookServiceImpl.getOne(..))")
    public void getOnePointcut() {}

    @Around("getOnePointcut()")
    public Integer handleExceptions(ProceedingJoinPoint joinPoint) throws Throwable, Exception {
        try {
            return (Integer) joinPoint.proceed();
        } catch(IndexOutOfBoundsException e) {
            throw new RuntimeException("No such book.");
        }
    }
}
