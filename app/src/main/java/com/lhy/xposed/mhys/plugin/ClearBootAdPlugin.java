package com.lhy.xposed.mhys.plugin;

import android.app.Activity;

import com.lhy.xposed.mhys.HYHelper;
import com.lhy.xposed.mhys.LogUtil;

import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

/**
 * 清理启动页的广告，5s启动
 * @time 2019年2月16日13:12:33
 */
public class ClearBootAdPlugin implements IPlugin {

    private final String splashActivityClassName = "com.mh.movie.core.mvp.ui.activity.SplashActivity";
    private final String constantsClassName = "com.mh.movie.core.mvp.ui.b";
    private final String loginResponseClassName = "com.mh.movie.core.mvp.model.entity.response.LoginResponse";
    private final String mainActivityClassName = "com.mh.movie.core.mvp.ui.activity.main.MainActivity";

    @Override
    public void run(final ClassLoader classLoader) throws Throwable {
        /**
         * 禁止加载广告历史记录
         */
        XposedHelpers.setStaticObjectField(classLoader.loadClass(constantsClassName), "t", "splash_no_ad_list");

        /**
         * 将AdList设置为空
         */
        XposedHelpers.findAndHookMethod(loginResponseClassName, classLoader, "getAdsList", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                LogUtil.e("hook getAdsList null!");
                param.setResult(null);
            }
        });

        /**
         * 在com.mh.movie.core.mvp.ui.activity.SplashActivity中
         * 替换a()方法，去除5s启动
         */
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
    }
}
