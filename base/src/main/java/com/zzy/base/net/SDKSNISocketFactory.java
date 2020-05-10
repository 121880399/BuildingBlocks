package com.zzy.base.net;

import android.net.SSLCertificateSocketFactory;
import android.net.SSLSessionCache;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.zzy.base.BaseApplication;
import com.zzy.base.utils.Utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/5/10 21:51
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class SDKSNISocketFactory extends SSLSocketFactory {
    private final String TAG = SDKSNISocketFactory.class.getSimpleName();

    private String bizHost;

    public boolean exceptionOccur = false;

    public SDKSNISocketFactory(String bizHost) {
        this.bizHost = bizHost;
    }
    @Override
    public Socket createSocket() throws IOException {
        return null;
    }
    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return null;
    }
    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
        return null;
    }
    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return null;
    }
    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return null;
    }
    // TLS layer
    @Override
    public String[] getDefaultCipherSuites() {
        return new String[0];
    }
    @Override
    public String[] getSupportedCipherSuites() {
        return new String[0];
    }
    @Override
    public Socket createSocket(Socket plainSocket, String host, int port, boolean autoClose) throws IOException {
        // 调用链
        // SDK -> urlConnection -> OkHttp -> SDK ->  SSLCertificateSocketFactory -> OpenSSL

        // 1. 减少一次三次握手
        // OkHttp调SDK, SDK调SSLCertificateSocketFactory创建SSLSocket方法区别：
        // createSocket(Socket k, String host, int port, boolean close)方法能复用之前在OkHttp层创建的plainSocket，减少一次三次握手
        // base on: https://android.googlesource.com/platform/frameworks/base/+/android-7.1.2_r23/core/java/android/net/SSLCertificateSocketFactory.java#493
        // base on: https://android.googlesource.com/platform/external/conscrypt/+/android-7.1.2_r27/src/main/java/org/conscrypt/OpenSSLSocketFactoryImpl.java#100
        //
        // 2. 证书校验问题
        // createSocket(Socket k, String host, int port, boolean close)方法在SSLCertificateSocketFactory层创建完SSLSocket后需要verifyHostname，此时会在SSLCertificateSocketFactory层握手，
        // 当回到OkHttp层再尝试握手时是已完成状态马上返回，SSLSession的peerHost是SDK传入的Host
        // base on: https://android.googlesource.com/platform/frameworks/base/+/android-7.1.2_r23/core/java/android/net/SSLCertificateSocketFactory.java#443
        // base on: https://android.googlesource.com/platform/frameworks/base/+/android-7.1.2_r23/core/java/android/net/SSLCertificateSocketFactory.java#189
        // base on: https://android.googlesource.com/platform/external/okhttp/+/android-7.1.1_r46/okhttp/src/main/java/com/squareup/okhttp/Connection.java#235
        //
        // 而createSocket(InetAddress addr, int port)在SSLCertificateSocketFactory层创建完SSLSocket后是不需要verifyHostname，
        // 所以需要在我们这层完成握手，否则如果回到OkHttp层再握手时，peerHost会被改变成我们传入url的Ip，导致在OkHttp层的校验失败
        // base on: https://android.googlesource.com/platform/frameworks/base/+/android-7.1.2_r23/core/java/android/net/SSLCertificateSocketFactory.java#474
        // base on: https://android.googlesource.com/platform/external/okhttp/+/android-7.1.1_r46/okhttp/src/main/java/com/squareup/okhttp/Connection.java#235
        //
        // 3. 兼容性问题
        // createSocket(Socket k, String host, int port, boolean close)方法在SSLCertificateSocketFactory层创建完SSLSocket后需要verifyHostname，此时会在SSLCertificateSocketFactory层握手，
        // OpenSSLContextImpl实现的不同导致，SSLCertificateSocketFactory层的握手在API23之前是不支持SNI的，API23之后支持SNI
        // base on: https://android.googlesource.com/platform/frameworks/base/+/android-6.0.1_r66/core/java/android/net/SSLCertificateSocketFactory.java#213
        // base on: https://android.googlesource.com/platform/frameworks/base/+/android-5.1.1_r6/core/java/android/net/SSLCertificateSocketFactory.java#211

        // 小结
        // 主要流程：OkHttp回调此类，此类调用SSLCertificateSocketFactory，SSLCertificateSocketFactory调用OpenSSL相关代码
        //
        // SDK调用SSLCertificateSocketFactory#createSocket(Socket k, String host, int port, boolean close)接口后，
        //      SSLCertificateSocketFactory调用完OpenSSLSocketFactoryImpl后，会做握手与证书校验，这个接口支持复用最早由OkHttp创建的plainSocket
        // SDK调用SSLCertificateSocketFactory#createSocket(InetAddress addr, int port)接口后，
        //      SSLCertificateSocketFactory不做其他动作，返回给SDK，SDK再返回给OkHttp，由OkHttp去握手和做证书校验，这个接口会新创建一个Socket
        //
        // 由于API23之前OpenSSL层实现导致SSLCertificateSocketFactory层的握手不支持SNI，所以需要用SSLCertificateSocketFactory#createSocket(InetAddress addr, int port)接口
        // 由于OkHttp层的握手会使用SDK传进url的host，即ip，所以当使用SSLCertificateSocketFactory#createSocket(InetAddress addr, int port)接口时，需要在SDK层先用业务host完成握手


        // ~~变更
        // SSLCertificateSocketFactory实例使用getInsecure接口，避免在SDK层设置参数前，SSLCertificateSocketFactory#createSocket(Socket k, String host, int port, boolean close)已经握手
        // 小结
        // 当前实现使用IP直连与使用域名效果，暂时一致

        if (TextUtils.isEmpty(bizHost)) {
            throw new IOException("SDK set empty bizHost");
        }

        Log.i(TAG, "customized createSocket. host: " + bizHost);
        try {
            if (Build.VERSION.SDK_INT < 17) {
                SSLSessionCache sslSessionCache = new SSLSessionCache(Utils.getContext());
                SSLCertificateSocketFactory sslSocketFactory = (SSLCertificateSocketFactory) SSLCertificateSocketFactory.getInsecure(10 * 1000, sslSessionCache);
                SSLSocket ssl = (SSLSocket) sslSocketFactory.createSocket(plainSocket, bizHost, port, autoClose);
                ssl.setEnabledProtocols(ssl.getSupportedProtocols());
                java.lang.reflect.Method setHostnameMethod = ssl.getClass().getMethod("setHostname", String.class);
                setHostnameMethod.invoke(ssl, bizHost);
                ssl.startHandshake();
                return ssl;
            } else {
                SSLSessionCache sessionCache = new SSLSessionCache(Utils.getContext());
                SSLCertificateSocketFactory sslSocketFactory = (SSLCertificateSocketFactory) SSLCertificateSocketFactory.getInsecure(10 * 1000, sessionCache);
                SSLSocket ssl = (SSLSocket) sslSocketFactory.createSocket(plainSocket, bizHost, port, autoClose);
                sslSocketFactory.setUseSessionTickets(ssl, true);
                ssl.setEnabledProtocols(ssl.getSupportedProtocols());
                sslSocketFactory.setHostname(ssl, bizHost);
                ssl.startHandshake();
                return ssl;
            }
        } catch (Throwable e) {
            // 发生任何异常时，抛给底层一个IO异常结束此次请求，使用域名重试，防止crash
            exceptionOccur = true;
            throw new IOException("SDKSNI exception: " + e);
        }
    }

    // 满足底层判断连接复用条件：equal(this.sslSocketFactory, that.sslSocketFactory)
    @Override
    public boolean equals(Object o) {
        if (TextUtils.isEmpty(bizHost) || !(o instanceof SDKSNISocketFactory)) {
            return false;
        }
        String thatHost = ((SDKSNISocketFactory) o).bizHost;
        if (TextUtils.isEmpty(thatHost)) {
            return false;
        }
        return bizHost.equals(thatHost);
    }
}
