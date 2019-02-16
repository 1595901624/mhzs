package com.lhy.xposed.mhys;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static com.lhy.xposed.mhys.Config.IS_FIRST_RUN;


public class HookMain {

    private final String splashActivityClassName = "com.mh.movie.core.mvp.ui.activity.SplashActivity";
    private final String playerPresenterClassName = "com.mh.movie.core.mvp.presenter.player.PlayerPresenter";
    private final String movieCardViewClassName = "com.mh.movie.core.mvp.ui.widget.MovieCardView";
    private final String mainActivityClassName = "com.mh.movie.core.mvp.ui.activity.main.MainActivity";
    private final String loginResponseClassName = "com.mh.movie.core.mvp.model.entity.response.LoginResponse";
    private final String constantsClassName = "com.mh.movie.core.mvp.ui.b";

    private final String playerActivityClassName = "com.mh.movie.core.mvp.ui.activity.player.PlayerActivity";
    private final String vipTvFragment = "com.mh.movie.core.mvp.ui.fragment.VipTvFragment";


    /**
     * 主函数
     *
     * @param param
     * @throws Throwable
     */
    public void main(XC_MethodHook.MethodHookParam param) throws Throwable {
        final Context context = (Context) param.args[0];
        final ClassLoader classLoader = context.getClassLoader();

        //进入麻花领空

        /**
         * 在com.mh.movie.core.mvp.ui.activity.SplashActivity中
         * 替换f()方法，去除5s启动
         */
//        XposedHelpers.findAndHookMethod(splashActivityClassName,
//                classLoader, "f", new XC_MethodReplacement() {
//                    @Override
//                    protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
//                        //跳转MainActivity，并结束当前进程
//                        Class clazz = classLoader.loadClass("com.mh.movie.core.mvp.ui.activity.main.MainActivity");
//                        HYHelper.startActivity((Activity) methodHookParam.thisObject, clazz);
//                        HYHelper.finish((Activity) methodHookParam.thisObject);
//                        return null;
//                    }
//                });

        /**
         * 禁止加载广告历史记录
         */
        XposedHelpers.setStaticObjectField(classLoader.loadClass(constantsClassName), "t", "splash_no_ad_list");


        /**
         * 将AdList设置为空
         */
        XposedHelpers.findAndHookMethod(loginResponseClassName, classLoader, "getAdsList", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                LogUtil.e("hook getAdsList null!");
                param.setResult(null);
            }
        });

        /**
         * 在com.mh.movie.core.mvp.ui.activity.SplashActivity中
         * 替换a()方法，去除5s启动
         */
        XposedHelpers.findAndHookMethod(splashActivityClassName, classLoader, "a", long.class, List.class, boolean.class, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                //跳转MainActivity，并结束当前进程
                Class clazz = classLoader.loadClass(mainActivityClassName);
                HYHelper.startActivity((Activity) methodHookParam.thisObject, clazz);
                HYHelper.finish((Activity) methodHookParam.thisObject);
                return null;
            }
        });

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

//        XposedHelpers.findAndHookMethod(vipTvFragment, classLoader, "a", String.class, int.class, new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
////                boolean a param.args[0];
//                param.args[0] = "true";
//                XposedBridge.log(param.args[0].toString() + " " + param.args[1].toString() + "--");
//            }
//        });

//        XposedHelpers.findAndHookMethod(playerActivityClassName, classLoader, "k", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                Activity playerActivity = (Activity) param.thisObject;
//                Field field = XposedHelpers.findField(playerActivity.getClass(), "T");
//                field.setAccessible(true);
//                field.setBoolean(playerActivity, true);
//                XposedBridge.log(field.getBoolean(playerActivity) + "----");
////                SharedPreferences sharedPreferences = playerActivity.getSharedPreferences("",);
//
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//            }
//        });

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
