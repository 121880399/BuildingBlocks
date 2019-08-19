package org.zzy.aoplib.async.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @作者 Zhouzhengyi
 * @创建日期 2019/8/12
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface Async {
}
