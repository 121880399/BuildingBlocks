package com.zzy.buildingblocks;

import android.app.Application;

import com.zzy.base.AppConfig;
import com.zzy.base.BaseApplication;

public class MainApplication extends BaseApplication{

    @Override
    public void onCreate() {
        super.onCreate();
        initModuleApplication(this);
    }

    @Override
    public void initModuleApplication(Application application) {
        for (String moduleApplication: AppConfig.moduleApplications) {
            try {
                Class clazz=Class.forName(moduleApplication);
                BaseApplication baseApplication= (BaseApplication) clazz.newInstance();
                baseApplication.initModuleApplication(this);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
