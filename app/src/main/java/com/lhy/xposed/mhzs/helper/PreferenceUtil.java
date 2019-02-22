package com.lhy.xposed.mhzs.helper;

import android.content.SharedPreferences;

public class PreferenceUtil {

    private static class PreferenceUtilHolder {
        private static PreferenceUtil INSTANCE = new PreferenceUtil();
    }

    private PreferenceUtil() {

    }

    public PreferenceUtil getInstance(){
        return PreferenceUtilHolder.INSTANCE;
    }

}
