package com.lhy.xposed.mhzs.helper;

import android.app.Activity;
import android.content.Intent;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;


public class HYHelper {
    /**
     * 拿到startAcitivity方法
     *
     * @param activity 当前的Activity
     * @param clazz    要跳转的Class
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void startActivity(Activity activity, Class<?> clazz) throws InvocationTargetException, IllegalAccessException {
        Intent intent = new Intent(activity, clazz);
        Method startActivityMethod = XposedHelpers.findMethodBestMatch(activity.getClass(), "startActivity", intent);
        startActivityMethod.setAccessible(true);
        startActivityMethod.invoke(activity, intent);
        XposedBridge.log(Config.TEST + "---" + startActivityMethod.getName() + "----");
    }

    /**
     * 结束当前Activity
     *
     * @param activity
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void finish(Activity activity) throws InvocationTargetException, IllegalAccessException {
        Method finishMethod = XposedHelpers.findMethodBestMatch(activity.getClass(), "finish");
        finishMethod.setAccessible(true);
        finishMethod.invoke(activity);
    }
}
