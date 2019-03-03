package com.lhy.xposed.mhzs.plugin;

import com.lhy.xposed.mhzs.helper.XPrefUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * 这是一个插件开发模板
 */
public class TemplatePlugin implements IPlugin {

    /**
     * 请在此进行Hook
     *
     * @param classLoader
     * @throws Throwable
     */
    @Override
    public void run(ClassLoader classLoader) throws Throwable {
        //Xposed框架的Hook方法
        XposedHelpers.findAndHookMethod("xxx", classLoader, "x", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });
    }

    /**
     * 插件的启用与关闭
     *
     * @return
     */
    @Override
    public boolean isOpen() {
//        return false;
        return XPrefUtils.getPref().getBoolean("xxx", false);
    }
}
