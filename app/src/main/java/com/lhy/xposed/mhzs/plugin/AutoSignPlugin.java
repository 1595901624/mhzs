package com.lhy.xposed.mhzs.plugin;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import com.lhy.xposed.mhzs.helper.Constant;
import com.lhy.xposed.mhzs.helper.LogUtil;
import com.lhy.xposed.mhzs.helper.XPrefUtils;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * 自动签到插件
 *
 * @author lhy
 * @time 2019年3月14日14:26:17
 * <p>
 * 该插件实现半自动签到，只有用户登录后且当点击到“任务”页面时，才会触发签到，否则不会签到。
 * <p>
 * <p>
 * * @version 1.1 2019年3月15日15:33:18
 * * 修改Application的获取方式
 */
public class AutoSignPlugin implements IPlugin {
    private final String $TaskWidget = "com.mh.movie.core.mvp.ui.widget.TaskWidget";
    private final String $TaskViewHolder = "com.mh.movie.core.mvp.ui.holder.ad";
    private final String $UserTaskResponse$ListBean = "com.mh.movie.core.mvp.model.entity.response.UserTaskResponse$ListBean";

    @Override
    public void run(final ClassLoader classLoader) throws Throwable {

        final Class $TaskViewHolderClass = classLoader.loadClass($TaskViewHolder);
        final Class $UserTaskResponse$ListBeanClass = classLoader.loadClass($UserTaskResponse$ListBean);

        XposedHelpers.findAndHookMethod(Constant.fgmt.$TaskFragment + "$3", classLoader, "a",
                $TaskViewHolderClass, $UserTaskResponse$ListBeanClass, int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);

                        Field taskActionTextViewField = XposedHelpers.findField($TaskViewHolderClass, "e");
                        taskActionTextViewField.setAccessible(true);
                        TextView taskActionTextView = (TextView) taskActionTextViewField.get(param.args[0]);

                        if (taskActionTextView.getText().toString().equals("点击签到")) {
                            //获取Application
                            Field f = XposedHelpers.findField($TaskViewHolderClass, "itemView");
                            f.setAccessible(true);
                            View view = (View) f.get(param.args[0]);
                            Application application = (Application) view.getContext().getApplicationContext();
                            SharedPreferences sp = application.getSharedPreferences("config", Context.MODE_PRIVATE);
                            boolean isLogin = sp.getBoolean("key_islogin", false);
                            LogUtil.e("isLogin = " + isLogin);

                            if (!isLogin)
                                return;
                            taskActionTextView.performClick();
                        }
                    }
                });

    }

    @Override
    public boolean isOpen() {
        return XPrefUtils.getPref().getBoolean("auto_sign", false);
    }
}
