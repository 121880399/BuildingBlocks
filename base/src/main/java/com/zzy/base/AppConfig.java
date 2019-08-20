package com.zzy.base;

public class AppConfig{
    private static final String LOGIN_APPLICATION="com.zzy.login.LoginApplication";
    private static final String PAY_APPLICATION="com.zzy.login.PayApplication";

    public static String[] moduleApplications={
            LOGIN_APPLICATION,
            PAY_APPLICATION
    };
}
