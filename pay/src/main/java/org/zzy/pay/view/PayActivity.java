package org.zzy.pay.view;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zzy.base.communication.login.factory.LoginServiceFactory;
import com.zzy.base.mvp.ui.BaseActivity;

import org.zzy.pay.R;
import org.zzy.pay.presenter.PayPresenter;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/8/20
 */
@Route(path = "/pay/payActivity")
public class PayActivity extends BaseActivity<PayPresenter> implements OnClickListener {

    private TextView mTvResultl;

    private Button mBtnPay;

    private Button mBtnGoLogin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initView() {
        mTvResultl = findViewById(R.id.tv_pay_result);
        mBtnPay = findViewById(R.id.btn_pay);
        mBtnGoLogin = findViewById(R.id.btn_goLogin);
    }

    @Override
    public void initData() {
        mBtnPay.setOnClickListener(this);
        mBtnGoLogin.setOnClickListener(this);
    }

    @Override
    public PayPresenter newPresenter() {
        return new PayPresenter();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_pay){
            mTvResultl.setVisibility(View.VISIBLE);
            if(LoginServiceFactory.getInstance().getLoginService().isLogin()){
                mTvResultl.setText("支付成功！");
            }else{
                mTvResultl.setText("您还没有登录！");
                mBtnGoLogin.setVisibility(View.VISIBLE);
            }
        }else if(v.getId() == R.id.btn_goLogin){
            ARouter.getInstance().build("/login/login").navigation();
        }
    }
}
