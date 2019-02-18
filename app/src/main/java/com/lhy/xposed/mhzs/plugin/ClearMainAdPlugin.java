package com.lhy.xposed.mhzs.plugin;

import com.lhy.xposed.mhzs.helper.LogUtil;

import java.util.List;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * 清理主界面广告
 * 包括：“推荐”，“电影”，“电视剧”
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
        try {
            XposedHelpers.findAndHookMethod(movieCardViewClassName, classLoader, "setShowBanner", List.class, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                    LogUtil.e("hook setShowBanner method!");
                    return null;
                }
            });
        } catch (Exception e) {
            LogUtil.e("hook setShowBanner Unknown Error!");
            XposedBridge.log(e);
        }

    }
}
