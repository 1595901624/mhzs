package com.lhy.xposed.mhzs.plugin;

import android.app.Activity;
import android.content.Intent;

import com.lhy.xposed.mhzs.helper.Constant;
import com.lhy.xposed.mhzs.helper.HYHelper;
import com.lhy.xposed.mhzs.helper.XPrefUtils;

import java.util.List;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

/**
 * 去除5s倒计时
 *
 * @time 2019年3月6日19:16:12
 */
public class ClearFiveSecondsPlugin implements IPlugin {

    @Override
    public void run(final ClassLoader classLoader) throws Throwable {
        /**
         * 在com.mh.movie.core.mvp.ui.activity.SplashActivity中
         * 替换a()方法，去除5s启动
         *
         * 此变量名版本更新可能会改变
         */
        XposedHelpers.findAndHookMethod(Constant.act.$SplashActivity, classLoader, "a", long.class, List.class, boolean.class, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                //跳转MainActivity，并结束当前进程
                Class clazz = classLoader.loadClass(Constant.act.$MainActivity);
                Activity activity = (Activity) methodHookParam.thisObject;

                Intent intent = new Intent(activity, clazz);
                XposedHelpers.callMethod(activity, "startActivity", intent);
                XposedHelpers.callMethod(activity, "finish");
                return null;
            }
        });
    }

    @Override
    public boolean isOpen() {
        return XPrefUtils.getPref().getBoolean("five_seconds", true);
    }
}
