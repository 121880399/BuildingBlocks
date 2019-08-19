package org.zzy.aoplib.delay.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.zzy.aoplib.delay.annotation.Delay;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/8/12
 */
@Aspect
public class DelayAspect {

    @Pointcut("execution(@org.zzy.aoplib.delay.annotation.Delay * *(..))")
    public void delayPointcut(){}

    @Around("delayPointcut() && @annotation(delay)")
    public Object dalayTask(final ProceedingJoinPoint joinPoint, Delay delay)throws Throwable{
        long delayTime = delay.delay();
        TimeUnit unit = delay.timeUnit();
        Object result = null;
        if(delayTime > 0){
            Disposable subscribe = Observable.timer(delayTime,unit)
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            try {
                                 joinPoint.proceed();
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }
                    });
        }else {
            result = joinPoint.proceed();
        }
        return result;
    }
}
