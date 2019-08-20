package com.zzy.base.aoplib.exception.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动tryCatch注解
 * @作者 ZhouZhengyi
 * @创建日期 2019/8/10
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoTryCatch {
}
