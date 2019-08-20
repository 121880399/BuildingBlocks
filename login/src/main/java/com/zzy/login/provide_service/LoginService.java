package com.zzy.login.provide_service;

import com.zzy.base.communication.login.service.ILoginService;

public class LoginService implements ILoginService {

    private static volatile LoginService mInstance;

    private boolean isLogin = false;

    private LoginService(){

    }

    public static LoginService getInstance(){
        if(mInstance == null){
            synchronized (LoginService.class){
                if(mInstance == null){
                    mInstance = new LoginService();
                }
            }
        }
        return mInstance;
    }

    @Override
    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login){
        isLogin = login;
    }
}
