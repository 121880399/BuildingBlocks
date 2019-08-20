package com.zzy.buildingblocks;

import android.app.Application;
import android.content.Context;

import com.zzy.base.AppConfig;
import com.zzy.base.BaseApplication;
import com.zzy.buildingblocks.init.ArouterTask;
import com.zzy.buildingblocks.init.GodEyeTask;
import com.zzy.buildingblocks.init.InitModuleTask;

import org.zzy.initiator.TaskDispatcher;
import org.zzy.initiator.utils.DispatcherExecutor;

public class MainApplication extends BaseApplication{

    public static MainApplication APP;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        APP = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TaskDispatcher dispatcher = new TaskDispatcher()
                .init(this)
                .addTask(new ArouterTask(this))
                .addTask(new InitModuleTask(this))
                .addTask(new GodEyeTask(this));
        dispatcher.start();
    }

    @Override
    public void initModuleApplication(Application application) {
       //在主APP中要对各Module的Application进行反射，所以放到了InitModuleTask中
        //用子线程执行，这个方法放在BaseApplication中的目的是为了让各module都要记得复写来提供
        //自己模块所要暴露的接口
    }
}
