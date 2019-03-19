package com.lhy.xposed.mhzs.plugin;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.lhy.xposed.mhzs.helper.Constant;
import com.lhy.xposed.mhzs.helper.LogUtil;
import com.lhy.xposed.mhzs.helper.XPrefUtils;

import java.util.Timer;
import java.util.TimerTask;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * 观看30分钟任务 插件
 * <p>
 * 播放任意一部影片即可完成任务
 *
 * @author lhy
 * @time 2019年3月14日17:06:50
 */
public class WatchThirtyMinutesPlugin implements IPlugin {
    @Override
    public void run(ClassLoader classLoader) throws Throwable {

        XposedHelpers.findAndHookMethod(Constant.$MHApplication, classLoader, "onCreate",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Application application = (Application) param.thisObject;
                        SharedPreferences sp = application.getSharedPreferences("config", Context.MODE_PRIVATE);
                        sp.edit().putLong("total_time", 1800002).commit();
                        LogUtil.e("Hook Movie Time");
                    }
                });

        XposedHelpers.findAndHookMethod(Timer.class.getName(), classLoader, "schedule", TimerTask.class,
                long.class, long.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        long delay = (long) param.args[1];
                        long period = (long) param.args[2];
                        if (delay == 180000 && period == 180000) {
                            param.args[1] = 0;
                        }
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                });
    }

    @Override
    public boolean isOpen() {
        return XPrefUtils.getPref().getBoolean("thirty_task", false);
    }
}
