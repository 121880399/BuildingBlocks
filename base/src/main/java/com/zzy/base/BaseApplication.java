package com.zzy.base;

import android.app.Application;

public abstract class BaseApplication extends Application {

    private static boolean mDebug = true;

    public static boolean isDebug(){
        return mDebug;
    }


    /**
     * Application初始化
     * */
    public abstract void initModuleApplication(Application application);
}
