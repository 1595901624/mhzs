package com.lhy.xposed.mhys.plugin;

import de.robv.android.xposed.XC_MethodHook;

public interface IPlugin {
    public void run(ClassLoader classLoader) throws Throwable;
}