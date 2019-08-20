package com.zzy.base.aoplib.schedule.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务
 * @作者 Zhouzhengyi
 * @创建日期 2019/8/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Scheduler {

    /**
     * 初始化延迟
     */
    long initDelay() default 0L;

    /**
     * 时间间隔
     */
    long interval() default 0L;

    /**
     * 单位
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 执行次数
     */
    int count() default Integer.MAX_VALUE;

    /**
     * 任务完成回调
     */
    String taskFinishCallback() default "";
}
