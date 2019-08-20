package org.zzy.pay;

import android.app.Application;

import com.zzy.base.BaseApplication;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/8/20
 */
public class PayApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initModuleApplication(this);
    }

    @Override
    public void initModuleApplication(Application application) {

    }
}
