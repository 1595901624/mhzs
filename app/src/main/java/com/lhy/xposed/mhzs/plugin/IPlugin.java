package com.lhy.xposed.mhzs.plugin;

public interface IPlugin {
    void run(ClassLoader classLoader) throws Throwable;
    boolean isOpen();
}