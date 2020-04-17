package org.zzy.life;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import org.zzy.life.fragment.LifeManagerFragment;
import org.zzy.life.fragment.SupportLifeManagerFragment;

import java.util.HashMap;
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

    public void with(Activity activity){
        FragmentManager fragmentManager = activity.getFragmentManager();
        LifeManagerFragment current = (LifeManagerFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if(current == null){
            current = lifeManagerFragments.get(fragmentManager);
            if(current == null){
                current = new LifeManagerFragment();
                lifeManagerFragments.put(fragmentManager,current);
            }
        }
        fragmentManager.beginTransaction().add(current,FRAGMENT_TAG).commitAllowingStateLoss();
    }

    public void with(Fragment fragment){

    }

    public void with(android.app.Fragment fragment){

    }

    public void with(FragmentActivity activity){

    }
}
