package com.zzy.buildingblocks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;

public class MainActivity extends AppCompatActivity {

    private Button mBtnGoLogin,mBtnGoPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnGoLogin = findViewById(R.id.btn_goLogin);
        mBtnGoPay = findViewById(R.id.btn_goPay);
        mBtnGoPay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/pay/payActivity").navigation();
            }
        });
        mBtnGoLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/login/login").navigation();
            }
        });
    }
}
