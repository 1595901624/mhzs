package com.lhy.xposed.mhzs.helper;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import de.robv.android.xposed.XposedHelpers;


public class HYHelper {

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
    public static String getVerisonName(Context context) {
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

    /***
     *
     */
    public static int getViewId(ClassLoader classLoader, String idName) throws ClassNotFoundException {
        return XposedHelpers.getStaticIntField(classLoader.loadClass(Constant.$id), "rl_main_partner");
    }

    /**
     * 获取设备信息
     *
     * @return
     */
    public static String getDeviceInfo() {
        return "设备品牌:" + Build.BRAND + " " + Build.MODEL + ", 安卓版本："
                + Build.VERSION.RELEASE + "(" + Build.VERSION.SDK_INT + ")";
    }

}
