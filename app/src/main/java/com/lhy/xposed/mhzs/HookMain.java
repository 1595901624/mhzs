package com.lhy.xposed.mhzs;

import android.content.Context;

import com.lhy.xposed.mhzs.plugin.ClearBootAdPlugin;
import com.lhy.xposed.mhzs.plugin.ClearMainAdPlugin;
import com.lhy.xposed.mhzs.plugin.ClearPlayerAdPlugin;
import com.lhy.xposed.mhzs.plugin.IPlugin;
import com.lhy.xposed.mhzs.plugin.VideoURLPlugin;
import com.lhy.xposed.mhzs.plugin.WatchHotMoviePlugin;
import com.lhy.xposed.mhzs.helper.Config;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class HookMain {

    /**
     * 主函数
     *
     * @param param
     * @throws Throwable
     */
    public void main(XC_MethodHook.MethodHookParam param) throws Throwable {
        final Context context = (Context) param.args[0];
        final ClassLoader classLoader = context.getClassLoader();

        //进入麻花领空,运行插件
        IPlugin[] iPlugins = new IPlugin[]{
                new ClearBootAdPlugin(),
                new ClearMainAdPlugin(),
                new ClearPlayerAdPlugin(),
                new WatchHotMoviePlugin(),
//                new InfiniteCachePlugin(),
                new VideoURLPlugin()
        };

        for (IPlugin iPlugin : iPlugins) {
            iPlugin.run(classLoader);
        }
    }


    /**
     * 只运行一次
     *
     * @param loadPackageParam
     */
    public void handleLoadPackage4release(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
        if (!Config.HOOK_APPLICATION_PACKAGE_NAME.equals(loadPackageParam.processName))
            return;

        XposedHelpers.findAndHookMethod("com.stub.StubApp", loadPackageParam.classLoader,
                "getOrigApplicationContext", Context.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        /**
                         * 获取360加固的CLASSLOADER
                         *
                         */
                        if (Config.IS_FIRST_RUN) {
                            //加锁防止多次运行
                            synchronized (HookMain.class) {
                                if (Config.IS_FIRST_RUN) {
                                    Config.IS_FIRST_RUN = false;
                                    //主函数
                                    main(param);
                                }
                            }
                        }

                    }
                });
    }

}
