package com.lhy.xposed.mhzs.plugin;

import android.app.Activity;

import com.lhy.xposed.mhzs.helper.HYHelper;
import com.lhy.xposed.mhzs.helper.LogUtil;
import com.lhy.xposed.mhzs.helper.XPrefUtils;

import java.util.List;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

public class ClearFiveSecondsPlugin implements IPlugin {
    private final String splashActivityClassName = "com.mh.movie.core.mvp.ui.activity.SplashActivity";
    private final String mainActivityClassName = "com.mh.movie.core.mvp.ui.activity.main.MainActivity";

    @Override
    public void run(final ClassLoader classLoader) throws Throwable {
        /**
         * 在com.mh.movie.core.mvp.ui.activity.SplashActivity中
         * 替换a()方法，去除5s启动
         *
         * 此变量名版本更新可能会改变
         */
        try {
            XposedHelpers.findAndHookMethod(splashActivityClassName, classLoader, "a", long.class, List.class, boolean.class, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                    //跳转MainActivity，并结束当前进程
                    Class clazz = classLoader.loadClass(mainActivityClassName);
                    HYHelper.startActivity((Activity) methodHookParam.thisObject, clazz);
                    HYHelper.finish((Activity) methodHookParam.thisObject);
                    return null;
                }
            });
        } catch (Exception e) {
            LogUtil.e("Boot 5s Unknown Error!");
        }
    }

    @Override
    public boolean isOpen() {
        return XPrefUtils.getPref().getBoolean("five_seconds", true);
    }
}
