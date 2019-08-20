package com.zzy.base.aoplib.singleClick.aspect;

import android.util.Log;
import android.view.View;

import com.zzy.base.R;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Calendar;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/8/12
 */
@Aspect
public class SingleClickAspect {

    private static String TAG = SingleClickAspect.class.getSimpleName();

    private static final int LAST_CLICK_KEY = R.string.lastClickKey;

    /**
     * 间隔时间
     */
    private static final long INTERVAL_TIME = 500L;

    /**
     * 直接是用onClick方法
     */
    @Pointcut("call(* android.view.View.OnClickListener.*(..))")
    public void onClickPointcut(){}

    /**
     * 在xml文件里定义android:onclick="xxx" 最后调用的是 DeclaredOnClickListener 这个类
     */
    @Pointcut("execution(* android.support.v7.app.AppCompatViewInflater.DeclaredOnClickListener.onClick(..))")
    public void onClickInXmlPointcut(){}

    /**
     * ButterKnife来实现onClick
     */
    @Pointcut("execution(@butterknife.OnClick * *(..))")
    public void onClickInButterKnifePointcut(){}

    @Around("onClickPointcut() || onClickInXmlPointcut() || onClickInButterKnifePointcut()")
    public void throttleClick(ProceedingJoinPoint joinPoint)throws Throwable{
        Object[] args = joinPoint.getArgs();
        View view = getViewFromArgs(args);
        //如果参数中没有View，则继续执行原方法不拦截
        if(view == null){
            Log.d(TAG,"unknown method,so proceed it");
            joinPoint.proceed();
            return;
        }
        Object tag = view.getTag(LAST_CLICK_KEY);
        long lastClickTime = tag == null ? 0 : (long)tag;
        long currentTime  = Calendar.getInstance().getTimeInMillis();
        if(currentTime - lastClickTime >= INTERVAL_TIME){
            view.setTag(LAST_CLICK_KEY,currentTime);
            joinPoint.proceed();
        }
    }


    /**
     * 获取 view 参数
     *
     * @param args
     * @return
     */
    private View getViewFromArgs(Object[] args) {
        if (args != null && args.length > 0) {
            Object arg = args[0];
            if (arg instanceof View) {
                return (View) arg;
            }
        }
        return null;
    }
}
