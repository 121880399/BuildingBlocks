package org.zzy.aoplib.TimeLog.aspect;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.concurrent.TimeUnit;

/**
 * @作者 Zhouzhengyi
 * @创建日期 2019/8/11
 */
@Aspect
public class TimeLogAspect {


    /**
     * 方法切入
     */
    @Pointcut("execution(@org.zzy.aoplib.TimeLog.annotation.TimeLog * *(..))")
    public void methodPointcut(){}

    /**
     * 构造方法切入
     */
    @Pointcut("execution(@org.zzy.aoplib.TimeLog.annotation.TimeLog *.new(..))")
    public void constructorPointcut(){}

    /**
     * 根据包名切入,根据是否全局打印来打开或关闭
     */
    @Pointcut("execution(* org.zzy.easyaop..*.*(..))")
    public void packagePointcut(){}

//    @Around("methodPointcut() || constructorPointcut() || packagePointcut()")
    @Around("packagePointcut()")
    public Object timeLog(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        StringBuilder stringBuilder = new StringBuilder()
                .append(signature.getMethod().getDeclaringClass().getCanonicalName())
                .append(":")
                .append(signature.getName());
        long startTime = System.nanoTime();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.nanoTime();
        stringBuilder.append("--->")
                .append("[")
                .append(TimeUnit.NANOSECONDS.toMillis(endTime-startTime))
                .append("ms]");
        Log.d("TimeLog",stringBuilder.toString());
        return result;
    }

}
