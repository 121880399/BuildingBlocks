package com.zzy.login.view;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zzy.base.mvp.ui.BaseActivity;
import com.zzy.login.R;
import com.zzy.login.presenter.LoginPresenter;
import com.zzy.login.provide_service.LoginService;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2018/11/19
 */
@Route(path = "/login/login")
public class LoginActivity extends BaseActivity<LoginPresenter> implements OnClickListener {

    private TextView mTvLoginDes;

    private Button mBtnLogin,mBtnExit,mBtnGoPay;

    @Override
    public int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    public void initView() {
        mTvLoginDes = findViewById(R.id.tv_loginDes);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnExit = findViewById(R.id.btn_exit);
        mBtnGoPay = findViewById(R.id.btn_goPay);
    }

    @Override
    public void initData() {
        mBtnLogin.setOnClickListener(this);
        mBtnGoPay.setOnClickListener(this);
        mBtnExit.setOnClickListener(this);
    }

    @Override
    public LoginPresenter newPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_exit){
            LoginService.getInstance().setLogin(false);
            mTvLoginDes.setText("这里是登录页面：未登录");
        }else if(v.getId() == R.id.btn_login){
            LoginService.getInstance().setLogin(true);
            mTvLoginDes.setText("这里是登录页面：已登录");
        }else if(v.getId() == R.id.btn_goPay){
            ARouter.getInstance().build("/pay/payActivity").navigation();
        }
    }
}
