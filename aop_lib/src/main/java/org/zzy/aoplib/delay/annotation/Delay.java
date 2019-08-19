package org.zzy.aoplib.delay.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 延迟任务
 * @作者 Zhouzhengyi
 * @创建日期 2019/8/12
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Delay {
    long delay() default 0L;

    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
