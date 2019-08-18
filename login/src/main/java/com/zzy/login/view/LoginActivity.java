package com.zzy.login.view;

import com.zzy.base.mvp.ui.BaseActivity;
import com.zzy.login.R;
import com.zzy.login.presenter.LoginPresenter;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2018/11/19
 */
public class LoginActivity extends BaseActivity<LoginPresenter> {

    @Override
    public int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    public void initData() {

    }

    @Override
    public LoginPresenter newPresenter() {
        return new LoginPresenter();
    }
}
