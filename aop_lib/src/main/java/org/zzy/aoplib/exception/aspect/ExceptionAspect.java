package org.zzy.aoplib.exception.aspect;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.zzy.aoplib.exception.annotation.AutoTryCatch;

/**
 * @作者 Zhouzhengyi
 * @创建日期 2019/8/10
 */
@Aspect
public class ExceptionAspect {

    /**
     * 按包名
     */
    private static final String PACKAGE_POINTCUT_METHOD = "execution(* org.zzy.easyaop..*.*(..))";

    /**
     * 按注解
     */
    private static final String ANNOTATION_POINTCUT_METHOD = "execution(@org.zzy.aoplib.exception.annotation"
            + ".AutoTryCatch * *(..))";

    /**
     * 可以根据是否全局trycatch来打开或关闭
     */
    @Pointcut(PACKAGE_POINTCUT_METHOD)
    public void packageAutoTryCatch(){}

    @Pointcut(ANNOTATION_POINTCUT_METHOD)
    public void annotationAutoTryCatch(){}

    @Around("packageAutoTryCatch()")
//    @Around("annotationAutoTryCatch()")
    public Object executeTryCatch(final ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
        Object obj=null;
        try {
            obj = proceedingJoinPoint.proceed();
        }catch (Throwable throwable) {
            Log.e("autoTryCatch",Log.getStackTraceString(throwable));
        }
        return obj;
    }
}
