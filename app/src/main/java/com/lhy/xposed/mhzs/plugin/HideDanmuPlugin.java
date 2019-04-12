package com.lhy.xposed.mhzs.plugin;


import android.content.Context;

import com.lhy.xposed.mhzs.helper.LogUtil;
import com.lhy.xposed.mhzs.helper.XPrefUtils;

import java.lang.reflect.Method;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * 默认隐藏弹幕
 * <p>
 * 弹幕设置按钮可开启
 *
 * @author lhy
 * @time 2019年4月12日10:49:16
 */
public class HideDanmuPlugin implements IPlugin {
    private String $DanmakuVideoPlayer = "com.mh.movie.core.mvp.ui.activity.player.DanmakuVideoPlayer";

    @Override
    public void run(final ClassLoader classLoader) throws Throwable {

        XposedHelpers.findAndHookMethod($DanmakuVideoPlayer, classLoader, "a", List.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                XposedHelpers.callMethod(param.thisObject, "d");
            }
        });

    }

    @Override
    public boolean isOpen() {
        return XPrefUtils.getPref().getBoolean("hide_danmu", false);
    }
}
