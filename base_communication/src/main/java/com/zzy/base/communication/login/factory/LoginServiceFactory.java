package com.zzy.base.communication.login.factory;

import com.zzy.base.communication.login.empty_service.EmptyLoginService;
import com.zzy.base.communication.login.service.ILoginService;

public class LoginServiceFactory {

    private ILoginService loginService;

    private LoginServiceFactory(){
    }

    private static class LoginFactoryInnerClass{
        private static LoginServiceFactory instance=new LoginServiceFactory();
    }

    public static LoginServiceFactory getInstance(){
        return LoginFactoryInnerClass.instance;
    }

    /**
     * 接收Login组件的Service实例
     * */
    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 返回Login组件的Service实例
     * */
    public ILoginService getLoginService() {
        if(loginService==null){
            loginService=new EmptyLoginService();
        }
        return loginService;
    }
}
