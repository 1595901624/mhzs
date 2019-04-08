package com.lhy.xposed.mhzs.plugin;

import com.lhy.xposed.mhzs.helper.Constant;
import com.lhy.xposed.mhzs.helper.XPrefUtils;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

/**
 * 清除MyFragment中的广告
 * <p>
 * 不会请求广告数据，从而消耗多余流量
 *
 * @author lhy
 * @time 2019年4月8日17:25:21
 */
public class ClearMyFragmentAdPlugin implements IPlugin {
    @Override
    public void run(ClassLoader classLoader) throws Throwable {
        XposedHelpers.findAndHookMethod(Constant.prst.$MyPresenter, classLoader, "e", XC_MethodReplacement.DO_NOTHING);
    }

    @Override
    public boolean isOpen() {
        return XPrefUtils.getPref().getBoolean("ad_my", true);
    }
}
