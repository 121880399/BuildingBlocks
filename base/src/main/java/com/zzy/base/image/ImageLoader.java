package com.zzy.base.image;

import android.content.Context;
import android.widget.ImageView;

import java.io.File;

/**
 * 项目名称: QuickMvp
 * 创建人: 周正一
 * 创建时间：2017/7/13
 * 此接口定义了图片加载框架要实现的功能，扩展的图片加载器都要实现该接口
 */

public interface ImageLoader {


    /**
     * 设置图片为圆形，包括默认的图片
     * */
    void loadCircleImage(String url, ImageView target, int resId);

    /**
     *
     *  从网络加载图片
     *  @param url 图片地址
     *  @param target  将图片加载到哪个ImageView
     **/
    void loadNet(String url, ImageView target);

    /**
     *
     *  从网络加载图片
     *  @param url 图片地址
     *  @param target  将图片加载到哪个ImageView
     *  @param options 里面包含默认占位符，错误占位符
     * */
    void loadNet(String url, ImageView target, Options options);

    /**
     * 从网络加载图片
     * @param callback 图片加载成功，失败，取消加载的回调
     * **/
    void loadNet(Context context, String url, Options options, LoadCallback callback);


    /**
     * 从资源加载图片
     *
     * */
    void loadResource(int resId, ImageView target, Options options);

    void loadAssets(String assetName, ImageView target, Options options);

    /**
     * 从文件加载图片
     * */
    void loadFile(File file, ImageView target, Options options);

    /**
     * 清空内存缓存
     * */
    void clearMemoryCache(Context context);

    /**
     * 清空硬盘缓存
     * */
    void clearDiskCache(Context context);

    /**
     * 恢复加载
     * */
    void resume(Context context);

    /**
     * 暂停加载
     * */
    void pause(Context context);

    class Options {
        public static final int RES_NONE = -1;
        public static final int DISK_ALL = 1;//所有的都在磁盘缓存
        public static final int DISK_NONE = 2;//所有的都不缓存
        public static final int DISK_SOURCE = 3;//只缓存源文件
        public static final int DISK_RESULT = 4;//只缓存转换后的文件

        public int loadingResId = RES_NONE;        //加载中的资源id,占位符
        public int loadErrorResId = RES_NONE;      //加载失败的资源id，加载失败占位符
        public ImageView.ScaleType scaleType = null;
        public int mDiskCacheStrategy;//硬盘缓存策略
        public boolean isSkipMemoryCache;//是否跳过内存缓存

        /**
         * 某人设置硬盘不缓存，并且跳过内存缓存
         * */
        public static Options defaultOptions() {
            return new Options(RES_NONE, RES_NONE,DISK_NONE,true);
        }

        public Options(int loadingResId, int loadErrorResId,int diskCacheStrategy,boolean isSkipMemoryCache) {
            this.loadingResId = loadingResId;
            this.loadErrorResId = loadErrorResId;
            this.mDiskCacheStrategy = diskCacheStrategy;
            this.isSkipMemoryCache=isSkipMemoryCache;
        }

        public Options scaleType(ImageView.ScaleType scaleType) {
            this.scaleType = scaleType;
            return this;
        }
    }
}
