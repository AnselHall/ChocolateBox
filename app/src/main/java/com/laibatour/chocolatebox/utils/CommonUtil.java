package com.laibatour.chocolatebox.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.laibatour.chocolatebox.globle.ChocolateApplication;

/**
 * 常用方法封装
 * @author Administrator
 */
public class CommonUtil {
    /**
     * 在主线程中执行runnable
     *
     * @param runnable
     */
    public static void runOnUIThread(Runnable runnable) {
        //Handler的post方法：Causes the Runnable r to be added to the message queue.
        // The runnable will be run on the thread to which this handler is attached.
        //post中运行的Runnable是运行在handler的线程中的
        ChocolateApplication.getMainHandler().post(runnable);
    }

    /**
     * 获取字符串资源
     * @param id
     * @return
     */
    public static String getString(int id) {
        return ChocolateApplication.getContext().getResources().getString(id);
    }

    /**
     * 获取dimens资源
     * @param id
     * @return
     */
    public static float getDimens(int id) {
        return ChocolateApplication.getContext().getResources().getDimension(id);
    }

    /**
     * 获取图片资源
     * @param id
     * @return
     */
    public static Drawable getDrawable(int id) {
        return ChocolateApplication.getContext().getResources().getDrawable(id);
    }

    /**
     * 获取字符串数组资源
     * @param id
     * @return
     */
    public static String[] getStringArray(int id) {
        return ChocolateApplication.getContext().getResources().getStringArray(id);
    }

    /**
     * 将view从父view中移除
     * @param view
     */
    public static void removeSelfFromParent(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(view);
            }
        }
    }

    /**
     * dp转换为px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, int dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        int px = (int) (dpValue * density + 0.5f);
        return px;
    }

}
