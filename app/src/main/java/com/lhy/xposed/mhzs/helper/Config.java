package com.lhy.xposed.mhzs.helper;

import java.util.HashSet;
import java.util.Set;

/**
 * 配置文件
 */
public interface Config {
    String HELP_MARKDOWN_URL = "https://raw.githubusercontent.com/1595901624/mhzs/master/README.md";
    Set<String> HOOK_APPLICATION_PACKAGE_NAME = new HashSet<String>() {{
        add("com.amahua.ywofnbfd");
        add("com.amahua.ompimdrt");
    }};
//    String HOOK_APPLICATION_PRE_PACKAGE_NAME = "com.amahua";

}
