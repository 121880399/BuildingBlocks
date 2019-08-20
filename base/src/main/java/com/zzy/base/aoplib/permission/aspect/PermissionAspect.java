package com.zzy.base.aoplib.permission.aspect;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zzy.base.aoplib.permission.annotation.PermissionRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import io.reactivex.functions.Consumer;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/8/9
 */
@Aspect
public class PermissionAspect {

    @Pointcut("execution(@org.zzy.aoplib.permission.annotation.PermissionRequest * *(..)) && @annotation"
            + "(permissionRequest)")
    public void requestPermission(PermissionRequest permissionRequest){}

    @Around("requestPermission(permissionRequest)")
    public void getPermission(final ProceedingJoinPoint proceedingJoinPoint,PermissionRequest permissionRequest){
        //获取到权限里申请的权限数组
        String[] permissions = permissionRequest.value();

        final Object target = proceedingJoinPoint.getTarget();
        RxPermissions rxPermissions = null;
        if(target instanceof Fragment){
            rxPermissions = new RxPermissions((Fragment)target);
        } else if(target instanceof FragmentActivity){
            rxPermissions = new RxPermissions((FragmentActivity)target);
        }
        if(rxPermissions!=null) {
            rxPermissions.request(permissions)
                         .subscribe(new Consumer<Boolean>() {
                             @Override
                             public void accept(Boolean granted) throws Exception {
                                 if(granted){
                                     try {
                                         proceedingJoinPoint.proceed();
                                     } catch (Throwable throwable) {
                                         Log.e("PermissionAspect",Log.getStackTraceString(throwable));
                                     }
                                 }else{
                                     Toast.makeText((Context)target,"未获取到权限",Toast.LENGTH_LONG).show();
                                 }
                             }
                         })  ;
        }


    }
}
