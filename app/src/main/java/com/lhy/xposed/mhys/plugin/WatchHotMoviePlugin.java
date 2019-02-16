package com.lhy.xposed.mhys.plugin;

import android.util.Log;

import com.lhy.xposed.mhys.LogUtil;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class WatchHotMoviePlugin implements IPlugin {

    private final static String detailResponseClassName = "com.mh.movie.core.mvp.model.entity.response.DetailResponse$VideoListBean";
    private final static String playerPresenterClassName = "com.mh.movie.core.mvp.presenter.player.PlayerPresenter";

    @Override
    public void run(ClassLoader classLoader) throws Throwable {
        XposedHelpers.findAndHookMethod(playerPresenterClassName, classLoader, "a", int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
//                LogUtil.e("Hook getPlayTy Method!");
////                param.setResult(null);pe
            }

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                LogUtil.e("Hook getPlayTy Method!");
                param.args[0] = 0;
            }
        });
    }
}
