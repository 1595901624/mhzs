package com.lhy.xposed.mhys;

import android.util.Log;

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
