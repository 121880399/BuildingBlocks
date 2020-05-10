package com.zzy.base.net;

import android.text.TextUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/5/10 21:49
 * 描    述：解决IP直连下的Https domain域名验证问题
 * 还有连接复用问题
 * 修订历史：
 * ================================================
 */
public class SDKHostnameVerifier implements HostnameVerifier {

    public String bizHost;

    public SDKHostnameVerifier(String bizHost) {
        this.bizHost = bizHost;
    }

    @Override
    public boolean verify(String hostname, SSLSession session) {
        return HttpsURLConnection.getDefaultHostnameVerifier().verify(bizHost, session);
    }

    @Override
    public boolean equals(Object o) {
        if (TextUtils.isEmpty(bizHost) || !(o instanceof SDKHostnameVerifier)) {
            return false;
        }
        String thatHost = ((SDKHostnameVerifier) o).bizHost;
        if (TextUtils.isEmpty(thatHost)) {
            return false;
        }
        return bizHost.equals(thatHost);
    }
}
