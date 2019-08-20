package com.zzy.base.aoplib.TimeLog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 打印方法耗时注解
 * @作者 Zhouzhengyi
 * @创建日期 2019/8/11
 */
@Target({ElementType.CONSTRUCTOR,ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface TimeLog {
}
