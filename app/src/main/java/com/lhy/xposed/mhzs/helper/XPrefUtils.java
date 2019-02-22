package com.lhy.xposed.mhzs.helper;

import com.lhy.xposed.mhzs.BuildConfig;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.robv.android.xposed.XSharedPreferences;

/**
 * Created by zpp0196 on 2018/4/11.
 */

public class XPrefUtils {

    private static WeakReference<XSharedPreferences> xSharedPreferences = new WeakReference<>(null);

    public static XSharedPreferences getPref() {
        XSharedPreferences preferences = xSharedPreferences.get();
        if (preferences == null) {
            preferences = new XSharedPreferences(BuildConfig.APPLICATION_ID);
            preferences.makeWorldReadable();
            preferences.reload();
            xSharedPreferences = new WeakReference<>(preferences);
        } else {
            preferences.reload();
        }
        return preferences;
    }

//    private static Set<String> getStringSet(String key) {
//        return getPref().getStringSet(key, new HashSet<>());
//    }
//
//    public static List<String> getStringList(String key) {
//        Set<String> set = getStringSet(key);
//        List<String> list = new ArrayList<>();
//        list.addAll(set);
//        return list;
//    }
//
//    public static List<Integer> getIntegerList(String key) {
//        List<String> stringList = getStringList(key);
//        List<Integer> integerList = new ArrayList<>();
//        for (String str : stringList) {
//            integerList.add(Integer.valueOf(str));
//        }
//        return integerList;
//    }
}
