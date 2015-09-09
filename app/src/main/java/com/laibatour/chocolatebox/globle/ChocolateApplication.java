package com.laibatour.chocolatebox.globle;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by Administrator on 2015/9/9.
 */
public class ChocolateApplication extends Application {

    private static Context mContext;//项目中公用的上下文对象
    private static Handler mainHandler;//主线程的Handler

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mainHandler = new Handler();
    }

    /**
     * 获取全局上下文对象
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 获取主线程的Handler
     *
     * @return
     */
    public static Handler getMainHandler() {
        return mainHandler;
    }
}
