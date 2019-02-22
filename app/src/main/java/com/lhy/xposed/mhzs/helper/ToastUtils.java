package com.lhy.xposed.mhzs.helper;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private ToastUtils() {

    }

    public static void toast(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toast(Activity activity, int resId) {
        Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
    }

    public static void toastLong(Activity activity, int resId) {
        Toast.makeText(activity, resId, Toast.LENGTH_LONG).show();
    }
}
