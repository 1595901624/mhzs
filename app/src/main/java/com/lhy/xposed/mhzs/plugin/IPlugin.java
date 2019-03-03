package com.lhy.xposed.mhzs.plugin;

/**
 * 如果想对现有的插件进行扩展，请实现此方法
 * <p>
 * 当然，您可以参照 TemplatePlugin 来开发
 * <p>
 * 插件写完以后，在HookMain中的iPlugins数组，加入自己的插件即可
 */
public interface IPlugin {
    /**
     * 插件运行方法
     * <p>
     * 请在此方法中进行Xposed Hook
     *
     * @param classLoader
     * @throws Throwable
     */
    void run(ClassLoader classLoader) throws Throwable;

    /**
     * 插件的启用与关闭
     * <p>
     * 1.可以直接返回true 或者 false
     * 2.可以添加配置文件，使用XPrefUtils.getPref().getBoolean("xxx", false)来返回结果
     *
     * @return
     */
    boolean isOpen();
}