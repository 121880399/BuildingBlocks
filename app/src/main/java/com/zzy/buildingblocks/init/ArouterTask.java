package com.zzy.buildingblocks.init;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zzy.buildingblocks.MainApplication;

import org.zzy.initiator.BuildConfig;
import org.zzy.initiator.task.Task;

/**
 * Arouter初始化
 * @作者 ZhouZhengyi
 * @创建日期 2019/8/20
 */
public class ArouterTask extends Task {

    private Application mApplication;

    public ArouterTask(Application application){
        mApplication = application;
    }

    @Override
    public void run() {
        //这两行必须写在init之前，否则这些配置在init过程中将无效
        if (MainApplication.isDebug()) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(mApplication);
    }
}
