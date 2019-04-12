package com.lhy.xposed.mhzs.plugin;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.lhy.xposed.mhzs.helper.Constant;
import com.lhy.xposed.mhzs.helper.LogUtil;
import com.lhy.xposed.mhzs.helper.XPrefUtils;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * 去除弹幕
 * <p>
 * 该功能开启后，内置弹幕设置开关将失效
 *
 * @time 2019年4月12日09:56:37
 * @auth lhy
 */
public class ClearDanmuPlugin implements IPlugin {
    private String $DanmakuVideoPlayer = "com.mh.movie.core.mvp.ui.activity.player.DanmakuVideoPlayer";

    @Override
    public void run(final ClassLoader classLoader) throws Throwable {
        XposedHelpers.findAndHookMethod($DanmakuVideoPlayer, classLoader, "g", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                LogUtil.e("clear danmuku ----");
                int llDanmakuViewId = XposedHelpers.getStaticIntField(classLoader.loadClass(Constant.$id), "ll_danmaku_view");
                View view = (View) param.thisObject;
                LinearLayout llDanmakuView = view.findViewById(llDanmakuViewId);
                llDanmakuView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean isOpen() {
        return XPrefUtils.getPref().getBoolean("clear_danmu", false);
    }
}
