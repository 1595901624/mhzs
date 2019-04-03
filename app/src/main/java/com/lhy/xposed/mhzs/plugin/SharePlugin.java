package com.lhy.xposed.mhzs.plugin;


import com.lhy.xposed.mhzs.helper.LogUtil;
import com.lhy.xposed.mhzs.helper.XPrefUtils;

import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * 假装分享插件
 * <p>
 * 点击即分享成功，将会取代WechatSharePlugin
 *
 * @author lhy
 * @time 2019年4月2日14:13:33
 */


public class SharePlugin implements IPlugin {
    private String $ShareManager = "com.mh.movie.core.c.a";


    @Override
    public void run(final ClassLoader classLoader) throws Throwable {
        //分享成功所调用的方法
        /*
           mo2835x();
           if (this.f2555s != null) {
               //参数f2538a代表以不同的方式分享
               // 1:微信朋友圈  2：微信  3:QQ  4:QQ空间  5：微博
               this.f2555s.mo2764d(this.f2538a);
           }
       */

        //微信朋友圈
        XposedHelpers.findAndHookMethod($ShareManager, classLoader, "o", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                XposedHelpers.callMethod(param.thisObject, "x");
                XposedHelpers.callMethod(XposedHelpers.getObjectField(param.thisObject, "s"), "d", 1);
                LogUtil.e("WeChat Friends share！");
                return null;
            }
        });

        //微信
        XposedHelpers.findAndHookMethod($ShareManager, classLoader, "p", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                XposedHelpers.callMethod(param.thisObject, "x");
                XposedHelpers.callMethod(XposedHelpers.getObjectField(param.thisObject, "s"), "d", 2);
                LogUtil.e("WeChat share！");
                return null;
            }
        });

        //QQ
        XposedHelpers.findAndHookMethod($ShareManager, classLoader, "q", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                XposedHelpers.callMethod(param.thisObject, "x");
                XposedHelpers.callMethod(XposedHelpers.getObjectField(param.thisObject, "s"), "d", 3);
                LogUtil.e("QQ share！");
                return null;
            }
        });

        //QQ空间
        XposedHelpers.findAndHookMethod($ShareManager, classLoader, "r", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                XposedHelpers.callMethod(param.thisObject, "x");
                XposedHelpers.callMethod(XposedHelpers.getObjectField(param.thisObject, "s"), "d", 4);
                LogUtil.e("QQ Zone share！");
                return null;
            }
        });

        //微博
        XposedHelpers.findAndHookMethod($ShareManager, classLoader, "s", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                XposedHelpers.callMethod(param.thisObject, "x");
                XposedHelpers.callMethod(XposedHelpers.getObjectField(param.thisObject, "s"), "d", 5);
                LogUtil.e("WeiBo share！");
                return null;
            }
        });

        //QQ用户提供代码
//        try {
//            Class claz = XposedHelpers.findClass("com.mh.movie.core.mvp.ui.activity.player.PlayerActivity", classLoader);
//            Method m = claz.getDeclaredMethod("x");
//            XposedBridge.log(m.getName());
//            XposedHelpers.findAndHookMethod("com.mh.movie.core.mvp.ui.activity.player.PlayerActivity", classLoader, "x", new XC_MethodHook() {
//
//                @Override
//                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
//                    super.beforeHookedMethod(param);
//                    XposedBridge.log("微信分享开始替换");
//                    Class shareManagerClazz = XposedHelpers.findClass("com.mh.movie.core.c.a", classLoader);
//                    Method shareManagerInstanceMethod = shareManagerClazz.getDeclaredMethod("i");
//                    Object shareManager = shareManagerInstanceMethod.invoke(null);
//                    Method methodShareWeChat = shareManager.getClass().getDeclaredMethod("g");
//                    methodShareWeChat.invoke(shareManager);
//                    //                    Method methodShareQQ = shareManager.getClass().getDeclaredMethod("a");
//                    //                    methodShareQQ.invoke(shareManager);
//                    //                    Method methodShareWeiBo = shareManager.getClass().getDeclaredMethod("d");
//                    //                    methodShareWeiBo.invoke(shareManager);
//                    param.setResult(null);
//                }
//            });
//        } catch (Throwable e) {
//            XposedBridge.log("微信分享错误");
//            XposedBridge.log(e.fillInStackTrace());
//        }
    }

    @Override
    public boolean isOpen() {
        return XPrefUtils.getPref().getBoolean("share", false);
    }
}
