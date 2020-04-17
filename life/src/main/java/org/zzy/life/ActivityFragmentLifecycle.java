package org.zzy.life;

import androidx.annotation.NonNull;

import org.zzy.life.interf.LifecycleListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/17 19:55
 * 描    述：
 * 修订历史：管理所有的生命周期监听器，并在相应的生命周期回调它们
 * 因为可能是异步，所以在遍历回调时，只遍历当前集合的副本文件，
 * 因为这时候用户有可能add监听进来，这样数据源会被改变，也是
 * 由于存在这种情况，所以在add方法中，还会对当前状态进行判断，
 * 然后调用新add进来的监听的相应方法。
 * ================================================
 */
public class ActivityFragmentLifecycle {
    private static final int CREATE_STATE = 0;
    private static final int START_STATE = 1;
    private static final int RESUME_STATE = 2;
    private static final int PAUSE_STATE = 3;
    private static final int STOP_STATE = 4;
    private static final int DESTROY_STATE = 5;
    private static final int NON_STATE = -1;

    /**
     * 保存所有生命周期监听器
     */
    private final Set<LifecycleListener> lifecycleListeners = Collections.newSetFromMap(new WeakHashMap<LifecycleListener,
            Boolean>());


    /**
     * 当前状态
     */
    private int currentState = NON_STATE;



    public void addListener(@NonNull LifecycleListener listener) {
        lifecycleListeners.add(listener);
        switch (currentState) {
            case CREATE_STATE:
                listener.onCreate();
                break;
            case START_STATE:
                listener.onStart();
                break;
            case RESUME_STATE:
                listener.onResume();
                break;
            case PAUSE_STATE:
                listener.onPause();
                break;
            case STOP_STATE:
                listener.onStop();
                break;
            case DESTROY_STATE:
                listener.onDestroy();
                break;
            default:
                break;
        }
    }


    public void removeListener(@NonNull LifecycleListener listener) {
        lifecycleListeners.remove(listener);
    }

    /**
     * 当Activity或Fragment的onCreate方法被调用
     * 时会回调该方法。
     */
    public void onCreate(){
        currentState = CREATE_STATE;
        for(LifecycleListener lifecycleListener : getSnapshot(lifecycleListeners)){
            lifecycleListener.onCreate();
        }
    }
    /**
     * 当Activity或Fragment的onStart方法被调用
     * 时会回调该方法。
     */
    public void onStart(){
        currentState = START_STATE;
        for(LifecycleListener lifecycleListener : getSnapshot(lifecycleListeners)){
            lifecycleListener.onStart();
        }
    }
    /**
     * 当Activity或Fragment的onResume方法被调用
     * 时会回调该方法。
     */
    public void onResume(){
        currentState = RESUME_STATE;
        for(LifecycleListener lifecycleListener : getSnapshot(lifecycleListeners)){
            lifecycleListener.onResume();
        }
    }
    /**
     * 当Activity或Fragment的onPause方法被调用
     * 时会回调该方法。
     */
    public void onPause(){
        currentState = PAUSE_STATE;
        for(LifecycleListener lifecycleListener : getSnapshot(lifecycleListeners)){
            lifecycleListener.onPause();
        }
    }
    /**
     * 当Activity或Fragment的onStop方法被调用
     * 时会回调该方法。
     */
    public void onStop(){
        currentState = STOP_STATE;
        for(LifecycleListener lifecycleListener : getSnapshot(lifecycleListeners)){
            lifecycleListener.onStop();
        }
    }
    /**
     * 当Activity或Fragment的onDestroy方法被调用
     * 时会回调该方法。
     */
    public void onDestroy(){
        currentState = DESTROY_STATE;
        for(LifecycleListener lifecycleListener : getSnapshot(lifecycleListeners)){
            lifecycleListener.onDestroy();
        }
    }

    /**
     * Returns a copy of the given list that is safe to iterate over and perform actions that may
     * modify the original list.
     *
     */
    @NonNull
    @SuppressWarnings("UseBulkOperation")
    private <T> List<T> getSnapshot(@NonNull Collection<T> other) {
        // toArray creates a new ArrayList internally and does not guarantee that the values it contains
        // are non-null. Collections.addAll in ArrayList uses toArray internally and therefore also
        // doesn't guarantee that entries are non-null. WeakHashMap's iterator does avoid returning null
        // and is therefore safe to use. See #322, #2262.
        List<T> result = new ArrayList<>(other.size());
        for (T item : other) {
            if (item != null) {
                result.add(item);
            }
        }
        return result;
    }
}
