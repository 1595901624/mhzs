package com.lhy.xposed.mhzs.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


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

    /**
     * 获取packageCode
     *
     * @param context
     * @return
     */
    public static long getPackageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        long code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.getLongVersionCode();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }


    /**
     * 获取packageName
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }
}
