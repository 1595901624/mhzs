package com.lhy.xposed.mhzs.plugin;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lhy.xposed.mhzs.helper.Constant;
import com.lhy.xposed.mhzs.helper.LogUtil;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class TVPlugin implements IPlugin {
    @Override
    public void run(final ClassLoader classLoader) throws Throwable {

        XposedHelpers.findAndHookMethod(Constant.act.$PlayerActivity, classLoader, "Z", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);

//                LogUtil.e("--TV Plugin--" + param.args[0]);
                Class r$idClazz = classLoader.loadClass(Constant.$id);
//                ll_detail_tag
                final int llDetailTagId = XposedHelpers.getStaticIntField(r$idClazz, "rl_player_introduce");
                final Object bPresent = XposedHelpers.findField(classLoader.loadClass(Constant.act.$PlayerActivity), "b");
                Activity activity = (Activity) param.thisObject;
                LinearLayout rootView = activity.findViewById(llDetailTagId);
                Button tvButton = new Button(activity);
                tvButton.setText("投屏");
                tvButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LogUtil.e("1123" + bPresent);

                    }
                });
                rootView.setOrientation(LinearLayout.HORIZONTAL);
                rootView.addView(tvButton, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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
