package org.zzy.aoplib.permission.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求权限注解
 * @作者 Zhouzhengyi
 * @创建日期 2019/8/8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionRequest {
    String[] value();
}
