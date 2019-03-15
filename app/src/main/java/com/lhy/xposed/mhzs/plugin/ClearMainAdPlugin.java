package com.lhy.xposed.mhzs.plugin;

import com.lhy.xposed.mhzs.helper.LogUtil;
import com.lhy.xposed.mhzs.helper.XPrefUtils;

import java.util.List;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

/**
 * 清理主界面广告
 * 包括：“推荐”，“电影”，“电视剧”
 *
 * 该插件全版本通用
 *
 * @author lhy
 * @time 2019年2月16日14:12:33
 */
public class ClearMainAdPlugin implements IPlugin {
    private final String movieCardViewClassName = "com.mh.movie.core.mvp.ui.widget.MovieCardView";

    @Override
    public void run(ClassLoader classLoader) throws Throwable {

        /**
         * 在com.mh.movie.core.mvp.ui.widget.MovieCardView中
         * 替换setShowBanner方法，去掉广告
         */
        // TODO: 2019/3/6 0006 VXP/EXP TOO SHORT METHOD
//        XposedHelpers.findAndHookMethod(movieCardViewClassName, classLoader, "setShowBanner", List.class, new XC_MethodReplacement() {
//            @Override
//            protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
//                LogUtil.e("hook setShowBanner method!");
//                return null;
//            }
//        });

        XposedHelpers.findAndHookMethod(movieCardViewClassName, classLoader, "c", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                LogUtil.e("hook setShowBanner2 method!");
                return null;
            }
        });

    }

    @Override
    public boolean isOpen() {
        return XPrefUtils.getPref().getBoolean("ad_main", true);
    }
}
