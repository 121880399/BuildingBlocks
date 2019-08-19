package org.zzy.aoplib.schedule.aspect;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.zzy.aoplib.schedule.annotation.Scheduler;
import org.zzy.aoplib.utils.reflect.Reflect;
import org.zzy.aoplib.utils.reflect.ReflectException;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @作者 Zhouzhengyi
 * @创建日期 2019/8/13
 */
@Aspect
public class ScheduleAspect {


    @Pointcut("execution(@org.zzy.aoplib.schedule.annotation.Scheduler * *(..))")
    public void schedulePointcut(){}

    @Around("schedulePointcut() && @annotation(scheduler)")
    public void scheduleExecution(final ProceedingJoinPoint joinPoint, Scheduler scheduler)throws Throwable{
        Object result = null;
        long initDelay = scheduler.initDelay();
        long interval = scheduler.interval();
        TimeUnit timeUnit = scheduler.timeUnit();
        int count = scheduler.count();
        final String taskFinishCallback = scheduler.taskFinishCallback();

        Observable.intervalRange(0,count,initDelay,interval,timeUnit)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        try {
                            Reflect.on(joinPoint.getTarget()).call(taskFinishCallback);
                        }catch (ReflectException e){
                            Log.e("ScheduleAspect",Log.getStackTraceString(e));
                        }
                    }
                });
    }

}
