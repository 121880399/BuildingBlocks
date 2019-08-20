package com.zzy.login;

import android.app.Application;

import com.zzy.base.BaseApplication;
import com.zzy.base.communication.login.factory.LoginServiceFactory;
import com.zzy.login.provide_service.LoginService;

public class LoginApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initModuleApplication(this);
    }

    @Override
    public void initModuleApplication(Application application) {
        LoginServiceFactory.getInstance().setLoginService(LoginService.getInstance());
    }
}
