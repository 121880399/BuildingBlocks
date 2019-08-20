package com.zzy.base.aoplib.async.aspect;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @作者 Zhouzhengyi
 * @创建日期 2019/8/12
 */
@Aspect
public class AsyncAspect {

    @Pointcut("execution(@org.zzy.aoplib.async.annotation.Async * *(..))")
    public void onAsyncMethod(){}

    @Around("onAsyncMethod()")
    public void asyncMethod(final ProceedingJoinPoint joinPoint)throws Throwable{
        Flowable.create(new FlowableOnSubscribe<Object>() {
            @Override
            public void subscribe(FlowableEmitter<Object> emitter) throws Exception {
                try{
                    joinPoint.proceed();
                }catch (Throwable throwable){
                    Log.e("async",Log.getStackTraceString(throwable));
                }
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
