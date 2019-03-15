package com.lhy.xposed.mhzs.plugin;

import com.lhy.xposed.mhzs.helper.Constant;
import com.lhy.xposed.mhzs.helper.LogUtil;
import com.lhy.xposed.mhzs.helper.XPrefUtils;

import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * 去除主界面提示升级的对话框
 * <p>
 * 该插件全版本通用
 *
 * @author lhy
 * @time 2019年2月27日16:22:31
 */
public class NoUpdatePlugin implements IPlugin {
    private final String channelResponseClassName = "com.mh.movie.core.mvp.model.entity.response.ChannelResponse";

    @Override
    public void run(ClassLoader classLoader) throws Throwable {
        /**
         * 方法1：直接返回null
         */
        // TODO: 2019/2/27 0027 与 EXP/VXP 可能会冲突
//        XposedHelpers.findAndHookMethod(channelResponseClassName, classLoader, "getUrl", new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                LogUtil.e("Hook ChannelResponse!");
//                param.setResult(null);
//            }
//        });

        /**
         * 方法2：另辟蹊径
         *
         * 此变量名版本更新可能会改变
         */
        final Class channelResponseClazz = classLoader.loadClass(channelResponseClassName);
        XposedHelpers.findAndHookMethod(Constant.act.$MainActivity, classLoader, "a", channelResponseClazz, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                LogUtil.e("Hook MainActivity No Update!");
                Object o = channelResponseClazz.cast(param.args[0]);
                XposedHelpers.findMethodBestMatch(channelResponseClazz, "setUrl", String.class);
                Method setUrlMethod = channelResponseClazz.getMethod("setUrl", String.class);
                setUrlMethod.invoke(o, "");
//                XposedBridge.invokeOriginalMethod(setUrlMethod, o, new Object[]{""});
            }
        });
    }

    @Override
    public boolean isOpen() {
        return XPrefUtils.getPref().getBoolean("no_update", false);
    }
}
