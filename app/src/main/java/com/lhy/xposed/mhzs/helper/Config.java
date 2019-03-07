package com.lhy.xposed.mhzs.helper;

import java.util.HashSet;
import java.util.Set;

public class Config {
    public static boolean IS_FIRST_RUN = true;
    public static int TEST = 0;
    public static final Set<String> HOOK_APPLICATION_PACKAGE_NAME = new HashSet<String>() {{
        add("com.amahua.ywofnbfd");
        add("com.amahua.ompimdrt");
    }};
    public static final String HOOK_APPLICATION_PRE_PACKAGE_NAME = "com.amahua";

//    public static boolean MHZS_GLOBAL_SET = true;
}
