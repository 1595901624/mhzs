package com.lhy.xposed.mhys.plugin;

import com.lhy.xposed.mhys.LogUtil;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

public class ClearPlayerAdPlugin implements IPlugin {
    private final String playerPresenterClassName = "com.mh.movie.core.mvp.presenter.player.PlayerPresenter";

    @Override
    public void run(ClassLoader classLoader) throws Throwable {
        /**
         * 在com.mh.movie.core.mvp.presenter.player.PlayerPresenter中
         * 替换f()方法，去除播放页面的广告
         */
        XposedHelpers.findAndHookMethod(playerPresenterClassName, classLoader, "f", int.class, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                try {
                    LogUtil.e("hook f method!");
                } catch (Exception e) {

                }
                return null;
            }
        });
    }
}
