package com.lhy.xposed.mhzs.plugin;

import android.util.Log;

import com.lhy.xposed.mhzs.helper.LogUtil;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * @author lhy
 */
@Deprecated
public class InfiniteCachePlugin implements IPlugin {

    private final String userInfoResponseClassName = "com.mh.movie.core.mvp.model.entity.response.UserInfoResponse";
    private final String cacheViewClassName = "com.mh.movie.core.mvp.ui.widget.cache.CacheView";
    private final String videoListBeanClassName = "com.mh.movie.core.mvp.model.entity.response.DetailResponse$VideoListBean";

    @Override
    public void run(final ClassLoader classLoader) throws Throwable {
        XposedHelpers.findAndHookMethod(userInfoResponseClassName, classLoader, "getCanCacheNum", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                LogUtil.e("Hook getRestCacheNum Method!");
                param.setResult(-1);
            }
        });

        XposedHelpers.findAndHookMethod(cacheViewClassName, classLoader, "b", int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                LogUtil.e("--cache id--" + param.args[0]);
            }
        });

        XposedHelpers.findAndHookMethod(videoListBeanClassName, classLoader, "getVideoState", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                LogUtil.e("--getVideoState--" + param.getResult());
            }
        });

        XposedHelpers.findAndHookMethod(cacheViewClassName, classLoader, "a", boolean.class, int.class, classLoader.loadClass(videoListBeanClassName), new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Field yField = XposedHelpers.findField(classLoader.loadClass(cacheViewClassName), "y");
                yField.setAccessible(true);
                yField.setInt(param.thisObject, 9);


//                int y = yField.getInt(param.thisObject);
                LogUtil.e("---" + param.args[0] + "---");
                LogUtil.e("public void mo9800a(boolean z, int i, VideoListBean videoListBean)   ");
            }
        });


    }

    @Override
    public boolean isOpen() {
        return true;
    }
}
