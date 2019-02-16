package com.lhy.xposed.mhys.plugin;

import com.lhy.xposed.mhys.LogUtil;

import java.util.List;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

public class ClearMainAdPlugin implements IPlugin {
    private final String movieCardViewClassName = "com.mh.movie.core.mvp.ui.widget.MovieCardView";

    @Override
    public void run(ClassLoader classLoader) throws Throwable {
        /**
         * 在com.mh.movie.core.mvp.ui.widget.MovieCardView中
         * 替换setShowBanner方法，去掉广告
         */
        XposedHelpers.findAndHookMethod(movieCardViewClassName, classLoader, "setShowBanner", List.class, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                LogUtil.e("hook setShowBanner method!");
                return null;
            }
        });
    }
}
