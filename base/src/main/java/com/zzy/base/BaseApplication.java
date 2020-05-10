package com.zzy.base;

import android.app.Application;

import com.zzy.base.utils.Utils;

public abstract class BaseApplication extends Application {

    private static boolean mDebug = true;

    public static boolean isDebug(){
        return mDebug;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }

    /**
     * Application初始化
     * */
    public abstract void initModuleApplication(Application application);
}
