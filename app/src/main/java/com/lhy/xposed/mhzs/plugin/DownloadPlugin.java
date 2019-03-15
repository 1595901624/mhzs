package com.lhy.xposed.mhzs.plugin;

import com.lhy.xposed.mhzs.helper.Constant;
import com.lhy.xposed.mhzs.helper.LogUtil;

import java.lang.reflect.Field;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * download test
 *
 * @time 2019/03/13 pm
 */
public class DownloadPlugin implements IPlugin {
    private final String $DetailResponse = "com.mh.movie.core.mvp.model.entity.response.DetailResponse";
    private final String $CacheView = "com.mh.movie.core.mvp.ui.widget.cache.CacheView";

    @Override
    public void run(final ClassLoader classLoader) throws Throwable {
        XposedHelpers.findAndHookMethod($DetailResponse, classLoader, "getDownloadBean", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                LogUtil.e("=====");

            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod($CacheView, classLoader, "b",
                int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        Field u = XposedHelpers.findField(classLoader.loadClass($CacheView), "u");
                        u.setAccessible(true);
                        u.setBoolean(param.thisObject, false);

                        LogUtil.e("11111111111");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                });
        XposedHelpers.findAndHookMethod(Constant.prst.$PlayerPresenter, classLoader, "a",
                List.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        LogUtil.e("222222222222");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                });
    }

    @Override
    public boolean isOpen() {
        return true;
    }
}
