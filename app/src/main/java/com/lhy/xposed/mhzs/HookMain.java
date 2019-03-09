package com.lhy.xposed.mhzs;

import android.content.Context;

import com.lhy.xposed.mhzs.helper.LogUtil;
import com.lhy.xposed.mhzs.helper.XPrefUtils;
import com.lhy.xposed.mhzs.plugin.ClearBootAdPlugin;
import com.lhy.xposed.mhzs.plugin.ClearFiveSecondsPlugin;
import com.lhy.xposed.mhzs.plugin.ClearMainAdPlugin;
import com.lhy.xposed.mhzs.plugin.ClearMarqueePlugin;
import com.lhy.xposed.mhzs.plugin.ClearPlayerAdPlugin;
import com.lhy.xposed.mhzs.plugin.CustomMainInterfacePlugin;
import com.lhy.xposed.mhzs.plugin.IPlugin;
import com.lhy.xposed.mhzs.plugin.NoUpdatePlugin;
import com.lhy.xposed.mhzs.plugin.TVPlugin;
import com.lhy.xposed.mhzs.plugin.VideoURLPlugin;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class HookMain {
    private IPlugin[] iPlugins = new IPlugin[]{
            new ClearFiveSecondsPlugin(),
            new ClearBootAdPlugin(),
            new ClearMainAdPlugin(),
            new ClearPlayerAdPlugin(),
            new ClearMarqueePlugin(),
            new VideoURLPlugin(),
            new CustomMainInterfacePlugin(),
            new NoUpdatePlugin()
    };

    /**
     * 主函数
     *
     * @param param
     * @throws Throwable
     */
    public void main(XC_MethodHook.MethodHookParam param) throws Throwable {
        if (!XPrefUtils.getPref().getBoolean("global_set", true)) {
            LogUtil.e("Plugin is close!");
            return;
        }

        LogUtil.e("Plugin is open!");
        Context context = (Context) param.args[0];
        final ClassLoader classLoader = context.getClassLoader();

        //进入麻花领空,运行插件
        for (IPlugin iPlugin : iPlugins) {
            if (iPlugin.isOpen())
                iPlugin.run(classLoader);
        }
    }

    public void handleLoadPackage4release(final XC_LoadPackage.LoadPackageParam loadPackageParam) {

        XposedHelpers.findAndHookMethod("com.stub.StubApp", loadPackageParam.classLoader,
                "attachBaseContext", Context.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        /**
                         * 获取360加固的CLASSLOADER
                         *
                         */
                        //主函数
                        main(param);
                    }
                });
    }

}
