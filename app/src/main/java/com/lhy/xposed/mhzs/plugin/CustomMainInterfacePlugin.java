package com.lhy.xposed.mhzs.plugin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.lhy.xposed.mhzs.helper.LogUtil;
import com.lhy.xposed.mhzs.helper.XPrefUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * 自定义主界面的底部按钮
 *
 * @author lhy
 * @time 2019年2月18日11:51:33
 */
public class CustomMainInterfacePlugin implements IPlugin {
    private final String mainActivityClassName = "com.mh.movie.core.mvp.ui.activity.main.MainActivity";
    private final String r$idClassName = "com.mh.movie.core.R$id";

    @Override
    public void run(final ClassLoader classLoader) throws Throwable {

        try {
            XposedHelpers.findAndHookMethod(mainActivityClassName, classLoader, "c", Bundle.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    LogUtil.e("hook c method!");
                    super.beforeHookedMethod(param);
                    Class R$idClass = classLoader.loadClass(r$idClassName);
                    Activity mMainActivity = (Activity) param.thisObject;

                    if (XPrefUtils.getPref().getBoolean("tab2", false)) {
                        //第二个按钮
                        try {
                            int rlMainTVId = XposedHelpers.getStaticIntField(R$idClass, "rl_main_tv");
                            RelativeLayout relativeLayout = mMainActivity.findViewById(rlMainTVId);
                            relativeLayout.setVisibility(View.GONE);
                        } catch (Exception e) {
                            LogUtil.e("R.id.rl_main_tv Unknown Error!");
                        }
                    }

                    if (XPrefUtils.getPref().getBoolean("tab3", true)) {
                        //第三个按钮
                        try {
                            int rlMainPartnerId = XposedHelpers.getStaticIntField(R$idClass, "rl_main_partner");
                            RelativeLayout relativeLayout = mMainActivity.findViewById(rlMainPartnerId);
                            relativeLayout.setVisibility(View.GONE);
                        } catch (Exception e) {
                            LogUtil.e("R.id.rl_main_partner Unknown Error!");
                        }
                    }

                    if (XPrefUtils.getPref().getBoolean("tab4", false)) {
                        //第四个按钮
                        try {
                            int rlMainTaskId = XposedHelpers.getStaticIntField(R$idClass, "rl_main_task");
                            RelativeLayout relativeLayout = mMainActivity.findViewById(rlMainTaskId);
                            relativeLayout.setVisibility(View.GONE);
                        } catch (Exception e) {
                            LogUtil.e("R.id.rl_main_task Unknown Error!");
                        }
                    }

                    //                Field mListCheckBoxField = XposedHelpers.findField(classLoader.loadClass(mainActivityClassName), "q");
                    //                mListCheckBoxField.setAccessible(true);
                    //                List<CheckBox> list = (List<CheckBox>) mListCheckBoxField.get(mMainActivity);
                    //                for (CheckBox checkBox : list) {
                    //                    LogUtil.e(checkBox.isChecked() + "");
                    //                }
                    //                list.get(2).setVisibility(View.GONE);
                    //                mListCheckBoxField.set(mMainActivity, list);

                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public boolean isOpen() {
        return true;
    }
}
