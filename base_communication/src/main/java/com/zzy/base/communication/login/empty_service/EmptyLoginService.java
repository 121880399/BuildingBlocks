package com.zzy.base.communication.login.empty_service;

import com.zzy.base.communication.login.service.ILoginService;

public class EmptyLoginService implements ILoginService {
    @Override
    public boolean isLogin() {
        return false;
    }
}
