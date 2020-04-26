package org.zzy.life;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Looper;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import org.zzy.life.fragment.LifeManagerFragment;
import org.zzy.life.fragment.SupportLifeManagerFragment;
import org.zzy.life.interf.LifecycleListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/17 22:18
 * 描    述：生命周期入口类，设计成单例模式
 * 修订历史：
 * ================================================
 */
public class Lifecycle {
    private static final String FRAGMENT_TAG = "org.zzy.life";

    final Map<android.app.FragmentManager, LifeManagerFragment> lifeManagerFragments = new HashMap<>();

    final Map<androidx.fragment.app.FragmentManager, SupportLifeManagerFragment> supportLifeManagerFragments = new HashMap<>();

    private Lifecycle() {

    }

    private static class Holder {
        private static Lifecycle INSTANCE = new Lifecycle();
    }

    public static Lifecycle getInstance() {
        return Holder.INSTANCE;
    }

    public void with(Context context) {

    }

    public void with(Activity activity, LifecycleListener... listener) {
        assertNotDestroyed(activity);
        FragmentManager fragmentManager = activity.getFragmentManager();
        getLifeManagerFragment(fragmentManager, listener);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void with(Fragment fragment, LifecycleListener... listener) {
        if (fragment.getActivity() == null) {
            throw new IllegalArgumentException("You cannot start a load on a fragment before it is attached");
        }
        if (!isOnMainThread() || Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // TODO: 2020/4/26 不在主线程的情况
        } else {
            androidx.fragment.app.FragmentManager childFragmentManager = fragment.getChildFragmentManager();
            getSupportLifeManagerFragment(childFragmentManager, listener);
        }
    }

    public void with(android.app.Fragment fragment, LifecycleListener... listener) {
        if (fragment.getActivity() == null) {
            throw new IllegalArgumentException("You cannot start a load on a fragment before it is attached");
        }
        if (!isOnMainThread() || Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // TODO: 2020/4/26 不在主线程的情况
        } else {
            android.app.FragmentManager childFragmentManager = fragment.getChildFragmentManager();
            getLifeManagerFragment(childFragmentManager, listener);
        }
    }

    public void with(FragmentActivity activity, LifecycleListener... listener) {
        if (!isOnMainThread()) {

        } else {
            assertNotDestroyed(activity);
            androidx.fragment.app.FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
            getSupportLifeManagerFragment(supportFragmentManager, listener);
        }
    }

    private void getLifeManagerFragment(FragmentManager fragmentManager, LifecycleListener[] listener) {
        LifeManagerFragment current = (LifeManagerFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (current == null) {
            current = lifeManagerFragments.get(fragmentManager);
            if (current == null) {
                current = LifeManagerFragment.getInstance(Arrays.asList(listener));
                lifeManagerFragments.put(fragmentManager, current);
            }
        }
        fragmentManager.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss();
    }


    private void getSupportLifeManagerFragment(androidx.fragment.app.FragmentManager fragmentManager,
                                               LifecycleListener[] listeners) {
        SupportLifeManagerFragment current = (SupportLifeManagerFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (current == null) {
            current = supportLifeManagerFragments.get(fragmentManager);
            if (current == null) {
                current = SupportLifeManagerFragment.getInstance(Arrays.asList(listeners));
                supportLifeManagerFragments.put(fragmentManager, current);
            }
        }
        fragmentManager.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss();
    }

    public LifeManagerFragment getLifeManagerFragment(android.app.FragmentManager fm) {
        LifeManagerFragment current = (LifeManagerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (current == null) {
            current = lifeManagerFragments.get(fm);
        }
        return current;
    }

    public SupportLifeManagerFragment getLifeManagerFragment(androidx.fragment.app.FragmentManager fm) {
        SupportLifeManagerFragment current = (SupportLifeManagerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (current == null) {
            current = supportLifeManagerFragments.get(fm);
        }
        return current;
    }

    public void removeLifeManagerFragment(android.app.FragmentManager fm) {
        if(null != lifeManagerFragments) {
            lifeManagerFragments.remove(fm);
        }

    }

    public void removeSupportLifeManagerFragment(androidx.fragment.app.FragmentManager fm){
        if(null != supportLifeManagerFragments){
            supportLifeManagerFragments.remove(fm);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void assertNotDestroyed(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a load for a destroyed activity");
        }
    }

    /**
     * 判断是否在主线程
     * 作者: ZhouZhengyi
     * 创建时间: 2020/4/26 9:43
     */
    private boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
