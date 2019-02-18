package com.lhy.xposed.mhzs.helper;

import android.util.Log;

import com.lhy.xposed.mhzs.BuildConfig;

public class LogUtil {
    private final static String TAG = "XP_MHYSZY";

    private LogUtil() {
    }

    public static void e(String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, msg);
        }
    }

}
