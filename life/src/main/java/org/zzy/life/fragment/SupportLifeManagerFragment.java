package org.zzy.life.fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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
public class SupportLifeManagerFragment extends Fragment {

    private final ActivityFragmentLifecycle lifecycle;

    private androidx.fragment.app.FragmentManager mCurrentFM;

    public SupportLifeManagerFragment(){
        this(new ActivityFragmentLifecycle());
    }

    public SupportLifeManagerFragment(@NonNull ActivityFragmentLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    public static SupportLifeManagerFragment getInstance(List<LifecycleListener> listeners){
        SupportLifeManagerFragment fragment = new SupportLifeManagerFragment();
        for (LifecycleListener listener :listeners) {
            fragment.lifecycle.addListener(listener);
        }
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentActivity){
            mCurrentFM = ((FragmentActivity) context).getSupportFragmentManager();
        }
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
        Lifecycle.getInstance().removeSupportLifeManagerFragment(mCurrentFM);
    }




}
