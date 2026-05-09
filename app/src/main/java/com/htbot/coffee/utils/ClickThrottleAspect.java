package com.htbot.coffee.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ClickThrottleAspect {
    @Pointcut("execution(* android.view.View.OnClickListener.onClick(..))")
    public void onClickPointcut() {
    }

    @Around("onClickPointcut()")
    public void handleClick(ProceedingJoinPoint joinPoint) throws Throwable {
        long currentTime = System.currentTimeMillis();
        Object object = joinPoint.getArgs();
        joinPoint.proceed();
    }
}
