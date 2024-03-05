package com.kt.edu.thirdproject.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect // (1)
@Slf4j
@Profile("local") // (2)
@Component // (3)
public class LogAspect {

    @Pointcut("execution(* com.kt.edu.thirdproject.employee..*(..))") // (4)
    public void all() {
    }

    @Pointcut("execution(* com.kt.edu.thirdproject.employee.*Controller.*(..))") // (5)
    public void controller() {
    }

    @Pointcut("execution(* com.kt.edu.thirdproject.employee..*Service.*(..))") // (6)
    public void service() {
    }

    // Transactional Annotation 사용하는  method
    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void doTransaction(JoinPoint joinPoint) {
        log.info("[transaction] {} args = {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    // ktedu custom Annotation 사용하는  method
    @Before("@annotation(com.kt.edu.thirdproject.common.annotation.Ktedu)")
    public void doKtedu(JoinPoint joinPoint) {
        log.info("[KT Edu] {} args = {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @Around("all()") // (7)
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable { // (7-1)
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            return result;
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            log.info("log = {}" , joinPoint.getSignature());
            log.info("timeMs = {}", timeMs);
        }
    }

    @Before("controller() || service()") // (8)
    public void beforeLogic(JoinPoint joinPoint) throws Throwable { // (8-1)
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        log.info("before method = {}", method.getName());

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if(arg != null) {
                log.info("type = {}", arg.getClass().getSimpleName());
                log.info("value = {}", arg);
            }

        }
    }

    @After("controller() || service()") // (9)
    public void afterLogic(JoinPoint joinPoint) throws Throwable { // (9-1)
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        log.info("after method = {}", method.getName());

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if(arg != null) {
                log.info("type = {}", arg.getClass().getSimpleName());
                log.info("value = {}", arg);
            }

        }
    }
}