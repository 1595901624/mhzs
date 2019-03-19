package com.lhy.xposed.mhzs;

import com.lhy.xposed.mhzs.helper.Config;
import com.lhy.xposed.mhzs.helper.Constant;
import com.lhy.xposed.mhzs.helper.LogUtil;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.PathClassLoader;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class HookLoader implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!Config.HOOK_APPLICATION_PACKAGE_NAME.contains(loadPackageParam.packageName))
            return;

        // TODO: 2019/3/14 0014 检查模块运行状态3/3
//        hookModuleActive(loadPackageParam);

        try {
            //在发布时，直接调用即可。
            if (!BuildConfig.DEBUG) {
                HookMain hookMain = new HookMain();
                hookMain.handleLoadPackage4release(loadPackageParam);
                return;
            }
            //在调试模式为了不频繁重启，使用反射的方式调用自身的指定函数。

            /*【方法2】*/
            final String packageName = HookMain.class.getPackage().getName();
            String filePath = String.format("/data/app/%s-%s.apk", packageName, 1);
            if (!new File(filePath).exists()) {
                filePath = String.format("/data/app/%s-%s.apk", packageName, 2);
                if (!new File(filePath).exists()) {
                    filePath = String.format("/data/app/%s-%s/base.apk", packageName, 1);
                    if (!new File(filePath).exists()) {
                        filePath = String.format("/data/app/%s-%s/base.apk", packageName, 2);
                        if (!new File(filePath).exists()) {
                            XposedBridge.log("Error:在/data/app找不到APK文件" + packageName);
                            return;
                        }
                    }
                }
            }

            final PathClassLoader pathClassLoader = new PathClassLoader(filePath, ClassLoader.getSystemClassLoader());
            final Class<?> aClass = Class.forName(packageName + "." + HookMain.class.getSimpleName(), true, pathClassLoader);
            final Method aClassMethod = aClass.getMethod("handleLoadPackage4release", XC_LoadPackage.LoadPackageParam.class);
            aClassMethod.invoke(aClass.newInstance(), loadPackageParam);
        } catch (final Exception e) {
            XposedBridge.log(e);
        }
    }

    /**
     * 暂时不应用
     *
     * @param lpparam
     */
//    private void hookModuleActive(XC_LoadPackage.LoadPackageParam lpparam) {
//        if (lpparam.packageName.equals(BuildConfig.APPLICATION_ID)) {
//            XposedHelpers.findAndHookMethod(SettingFragment.class.getName(), lpparam.classLoader, "isModuleActive", XC_MethodReplacement.returnConstant(true));
//        }
//    }

}
