package com.lhy.xposed.mhzs.helper;

public class Constant {


    public static String CURRENT_HOOK_PACKAGE_NAME = "";
    public static final String $id = "com.mh.movie.core.R$id";
    public static final String $MHApplication = "com.mh.movie.core.mvp.ui.MHApplication";

    /**
     * 麻花影视的activity名
     */
    public static class act {
        public static final String $MainActivity = "com.mh.movie.core.mvp.ui.activity.main.MainActivity";
        public static final String $SplashActivity = "com.mh.movie.core.mvp.ui.activity.SplashActivity";
        public static final String $PlayerActivity = "com.mh.movie.core.mvp.ui.activity.player.PlayerActivity";
        public static final String $ScreeningActivity = "com.mh.movie.core.mvp.ui.activity.player.ScreeningActivity";

        public static final String $WXEntryActivity = CURRENT_HOOK_PACKAGE_NAME + ".wxapi.WXEntryActivity";
    }

    public static class fgmt {
        public static final String $TaskFragment = "com.mh.movie.core.mvp.ui.fragment.TaskFragment";
    }

    public static class prst {
        public static final String $PlayerPresenter = "com.mh.movie.core.mvp.presenter.player.PlayerPresenter";
    }

    public static class util {
        public static final String $MyTimeTaskHandler = "com.mh.movie.core.mvp.ui.utils.MyTimeTaskHandler";
    }

    public static class db {
        public static final String $TableCommodity = "com.mh.movie.core.mvp.model.db.TableCommodity";
    }

}
