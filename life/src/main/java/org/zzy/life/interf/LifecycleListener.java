package org.zzy.life.interf;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/17 19:56
 * 描    述：
 * 修订历史： 一个用于监听Fragment或者Activity生命周期的
 * 接口。
 * ================================================
 */
public interface LifecycleListener {

    /**
     * 当Activity或Fragment的onCreate方法被调用
     * 时会回调该方法。
     */
    void onCreate();
    /**
     * 当Activity或Fragment的onStart方法被调用
     * 时会回调该方法。
     */
    void onStart();
    /**
     * 当Activity或Fragment的onResume方法被调用
     * 时会回调该方法。
     */
    void onResume();
    /**
     * 当Activity或Fragment的onPause方法被调用
     * 时会回调该方法。
     */
    void onPause();
    /**
     * 当Activity或Fragment的onStop方法被调用
     * 时会回调该方法。
     */
    void onStop();
    /**
     * 当Activity或Fragment的onDestroy方法被调用
     * 时会回调该方法。
     */
    void onDestroy();
}
