package com.zzy.buildingblocks.init;

import android.app.Application;

import com.zzy.base.AppConfig;
import com.zzy.base.BaseApplication;

import org.zzy.initiator.task.Task;

/**
 * 初始化各Module的Application
 * 由于用到了反射，所以让它在子线程中执行
 * @作者 ZhouZhengyi
 * @创建日期 2019/8/19
 */
public class InitModuleTask extends Task {

    private Application mApplication;

    public InitModuleTask(Application application){
        mApplication = application;
    }

    @Override
    public void run() {
        initModuleApplication(mApplication);
    }

    public void initModuleApplication(Application application) {
        for (String moduleApplication: AppConfig.moduleApplications) {
            try {
                Class clazz=Class.forName(moduleApplication);
                BaseApplication baseApplication= (BaseApplication) clazz.newInstance();
                baseApplication.initModuleApplication(application);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean runOnMainThread() {
        return false;
    }
}
