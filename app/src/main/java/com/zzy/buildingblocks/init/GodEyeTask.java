package com.zzy.buildingblocks.init;

import android.app.Application;

import com.zzy.buildingblocks.MainApplication;

import org.zzy.initiator.task.Task;

import cn.hikyson.godeye.core.GodEye;
import cn.hikyson.godeye.core.GodEyeConfig;
import cn.hikyson.godeye.monitor.GodEyeMonitor;

/**
 * 初始化GodEye
 * @作者 ZhouZhengyi
 * @创建日期 2019/8/19
 */
public class GodEyeTask extends Task {
    private Application mApplication;

    public GodEyeTask(Application application){
        mApplication = application;
    }

    @Override
    public void run() {
        GodEye.instance().init(mApplication);
        GodEye.instance().install(GodEyeConfig.fromAssets("android-godeye-config/install.config"));
        GodEyeMonitor.work(mApplication);
    }

    @Override
    public boolean runOnMainThread() {
        return false;
    }
}
