package org.zzy.life.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.zzy.life.ActivityFragmentLifecycle;
import org.zzy.life.Lifecycle;
import org.zzy.life.interf.LifecycleListener;

import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/17 19:34
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LifeManagerFragment extends Fragment {

    private final ActivityFragmentLifecycle lifecycle;

    private android.app.FragmentManager mCurrentFM;

    public LifeManagerFragment() {
        this(new ActivityFragmentLifecycle());
    }

    @SuppressLint("ValidFragment")
    private LifeManagerFragment(ActivityFragmentLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    public static LifeManagerFragment getInstance(List<LifecycleListener> listeners){
        LifeManagerFragment fragment = new LifeManagerFragment();
        for (LifecycleListener listener :listeners) {
            fragment.lifecycle.addListener(listener);
        }
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCurrentFM = activity.getFragmentManager();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycle.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycle.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycle.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        lifecycle.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        lifecycle.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecycle.onDestroy();
        Lifecycle.getInstance().removeLifeManagerFragment(mCurrentFM);
    }


}
