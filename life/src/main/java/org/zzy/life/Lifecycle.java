package org.zzy.life;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import org.zzy.life.fragment.LifeManagerFragment;
import org.zzy.life.fragment.SupportLifeManagerFragment;
import org.zzy.life.interf.LifecycleListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/17 22:18
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class Lifecycle {
    private static final String FRAGMENT_TAG = "org.zzy.life";

    final Map<android.app.FragmentManager,LifeManagerFragment> lifeManagerFragments = new HashMap<>();

    final Map<androidx.fragment.app.FragmentManager, SupportLifeManagerFragment> supportLifeManagerFragments = new HashMap<>();

    public void with(Context context){

    }

    public void with(Activity activity, LifecycleListener... listener){
        assertNotDestroyed(activity);
        FragmentManager fragmentManager = activity.getFragmentManager();
        LifeManagerFragment current = (LifeManagerFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if(current == null){
            current = lifeManagerFragments.get(fragmentManager);
            if(current == null){
                current = LifeManagerFragment.getInstance(Arrays.asList(listener));
                lifeManagerFragments.put(fragmentManager,current);
            }
        }
        fragmentManager.beginTransaction().add(current,FRAGMENT_TAG).commitAllowingStateLoss();
    }

    public void with(Fragment fragment, LifecycleListener... listener){
        if (fragment.getActivity() == null) {
            throw new IllegalArgumentException("You cannot start a load on a fragment before it is attached");
        }
    }

    public void with(android.app.Fragment fragment, LifecycleListener... listener){

    }

    public void with(FragmentActivity activity, LifecycleListener... listener){

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void assertNotDestroyed(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a load for a destroyed activity");
        }
    }
}
