package com.zzy.base;

import android.app.Application;

public abstract class BaseApplication extends Application {

    /**
     * Application初始化
     * */
    public abstract void initModuleApplication(Application application);
}
