package com.lhy.xposed.mhzs.plugin;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lhy.xposed.mhzs.helper.Constant;
import com.lhy.xposed.mhzs.helper.LogUtil;
import com.lhy.xposed.mhzs.helper.XPrefUtils;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * TV投屏功能
 * @time 2019年3月10日
 * @author lhy
 */
public class TVPlugin implements IPlugin {
    @Override
    public void run(final ClassLoader classLoader) throws Throwable {

        XposedHelpers.findAndHookMethod(Constant.prst.$PlayerPresenter, classLoader, "c", int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                LogUtil.e("--TV Plugin--" + param.args[0]);
                if ((int) param.args[0] == 1 || (int) param.args[0] == 9) {
                    Class tableCommodityClazz = classLoader.loadClass(Constant.db.$TableCommodity);
                    Object object = tableCommodityClazz.newInstance();
                    Field hasFlag = XposedHelpers.findField(tableCommodityClazz, "hasFlag");
                    hasFlag.setAccessible(true);
                    hasFlag.setInt(object, 1);
                    Boolean b = XposedHelpers.getStaticBooleanField(classLoader.loadClass(Constant.act.$PlayerActivity), "q");
                    LogUtil.e("----" + b);
                    param.setResult(object);
                }
            }
        });
    }

    @Override
    public boolean isOpen() {
        return XPrefUtils.getPref().getBoolean("tv_screen", false);
    }
}
