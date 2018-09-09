package com.zzy.login.provide_service;

import com.zzy.base.communication.login.service.ILoginService;

public class LoginService implements ILoginService {

    @Override
    public boolean isLogin() {
        return true;
    }
}
