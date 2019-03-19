package com.lhy.xposed.mhzs;

import android.app.Application;

import cn.bmob.v3.Bmob;

public class MHApplicaiton extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "0053ab880ceb939700414994235c4adf");
    }
}
