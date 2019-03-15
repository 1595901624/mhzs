package com.lhy.xposed.mhzs.plugin;

import android.os.Bundle;
import android.os.Message;

import com.lhy.xposed.mhzs.helper.Constant;
import com.lhy.xposed.mhzs.helper.LogUtil;
import com.lhy.xposed.mhzs.helper.XPrefUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * 清除底部滚动广告
 *
 * @author lhy
 * @time 2019年3月7日13:37:05
 */
// TODO: 2019/3/15 0015 BUG
public class ClearMarqueePlugin implements IPlugin {

    @Override
    public void run(ClassLoader classLoader) throws Throwable {
        XposedHelpers.findAndHookMethod(Constant.util.$MyTimeTaskHandler, classLoader, "handleMessage", Message.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Message msg = (Message) param.args[0];
//                LogUtil.e("msg.what = " + msg.what);
                if (msg.what == 9999) {
                    msg.what = 5211314;
//                    LogUtil.e("ClearMarqueePlugin run");
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
        return XPrefUtils.getPref().getBoolean("ad_marquee", false);
    }
}
