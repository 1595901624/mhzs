package com.lhy.xposed.mhzs.plugin;

import android.app.Activity;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lhy.xposed.mhzs.helper.LogUtil;
import com.lhy.xposed.mhzs.bean.VideoInfoBean;
import com.lhy.xposed.mhzs.helper.ToastUtils;
import com.lhy.xposed.mhzs.helper.XPrefUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * 获取视频真实地址 1.2
 *
 * @author lhy
 * @time 2019年2月18日11:58:37
 * @update 1.1 2019年2月27日21:15:48
 * @update 2.0 2019年3月3日15:02:32
 * 在PlayerActivity中添加按钮获取地址：
 */
public class VideoURLPlugin implements IPlugin {
    private final String resultStrHandleSubscriberClassName = "com.mh.movie.core.mvp.model.a.b";
    private final String aesUtilClassName = "com.mh.movie.core.mvp.ui.utils.AesUtil";
    private final String playerActvityClassName = "com.mh.movie.core.mvp.ui.activity.player.PlayerActivity";
    private final String r$idClassName = "com.mh.movie.core.R$id";
    private final String playerPresenterClassName = "com.mh.movie.core.mvp.presenter.player.PlayerPresenter";
    private final String videoAddressResponseClassName = "com.mh.movie.core.mvp.model.entity.response.VideoAddressResponse";
    private final String m3u8FormatBeanClassName = "com.mh.movie.core.mvp.model.entity.M3u8FormatBean";

    private String $1080PPlayUrl;
    private String $720PPlayUrl;
    private String $480PPlayUrl;
    private String $360PPlayUrl;

    @Override
    public void run(ClassLoader classLoader) throws Throwable {
        parseVideoUrl(classLoader);
        addExtraLayout(classLoader);
    }

    /***
     * 添加布局
     * @param classLoader
     * @throws ClassNotFoundException
     */
    private void addExtraLayout(ClassLoader classLoader) throws ClassNotFoundException {
        Class r$idClazz = classLoader.loadClass(r$idClassName);
        final int clPlayerRootId = XposedHelpers.getStaticIntField(r$idClazz, "cl_player_root");
        final int rlPlayerIntroduceId = XposedHelpers.getStaticIntField(r$idClazz, "rl_player_introduce");
        XposedHelpers.findAndHookMethod(playerActvityClassName, classLoader, "Z", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                //获取根布局
                final Activity activity = (Activity) param.thisObject;
                RelativeLayout rootView = activity.findViewById(rlPlayerIntroduceId);
                //创建剪切板
                final ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);

                //放置四个按钮获取地址
                Button $360PBtn = new Button(activity);
                $360PBtn.setText("360P");
                $360PBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ($360PPlayUrl != null) {
                            ToastUtils.toast(activity, $360PPlayUrl);
                            ClipData clipData = ClipData.newPlainText(null, $360PPlayUrl);
                            clipboard.setPrimaryClip(clipData);
                        } else
                            ToastUtils.toast(activity, "无法获取到播放地址！");
                    }
                });

                Button $480PBtn = new Button(activity);
                $480PBtn.setText("480P");
                $480PBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ($480PPlayUrl != null) {
                            ToastUtils.toast(activity, $480PPlayUrl);
                            ClipData clipData = ClipData.newPlainText(null, $480PPlayUrl);
                            clipboard.setPrimaryClip(clipData);
                        } else
                            ToastUtils.toast(activity, "无法获取到播放地址！");
                    }
                });

                Button $720PBtn = new Button(activity);
                $720PBtn.setText("720P");
                $720PBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ($720PPlayUrl != null) {
                            ToastUtils.toast(activity, $720PPlayUrl);
                            ClipData clipData = ClipData.newPlainText(null, $720PPlayUrl);
                            clipboard.setPrimaryClip(clipData);
                        } else {
                            ToastUtils.toast(activity, "无法获取到播放地址！");
                        }
                    }
                });

                Button $1080PBtn = new Button(activity);
                $1080PBtn.setText("1080P");
                $1080PBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ($1080PPlayUrl != null) {
                            ToastUtils.toast(activity, $1080PPlayUrl);
                            ClipData clipData = ClipData.newPlainText(null, $1080PPlayUrl);
                            clipboard.setPrimaryClip(clipData);
                        } else
                            ToastUtils.toast(activity, "无法获取到播放地址！");
                    }
                });

                //使用LinearLayout包含四个按钮
                LinearLayout view = new LinearLayout(activity);
                view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));//设置布局参数
                view.setOrientation(LinearLayout.HORIZONTAL);
                view.addView($360PBtn);
                view.addView($480PBtn);
                view.addView($720PBtn);
                view.addView($1080PBtn);

                //将LinearLayout加入到根布局
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
//                params.rightMargin = 30;
                rootView.addView(view, params);

                LogUtil.e("布局入侵！！！！");
            }
        });
    }

    /**
     * 解析视频真实地址(适用于麻花影视2.5.0前)
     * /api/app/video/ver2/user/clickPlayVideo_2_2/
     *
     * @param classLoader
     * @throws ClassNotFoundException
     */
    @Deprecated
    private void getVideoURL(final ClassLoader classLoader) throws ClassNotFoundException {
        final Class aesUtilClazz = classLoader.loadClass(aesUtilClassName);
        XposedHelpers.findAndHookMethod(resultStrHandleSubscriberClassName, classLoader, "a", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);

                String decryptHex = (String) XposedHelpers.callStaticMethod(aesUtilClazz, "decryptHex", param.args[0] + "",
                        XposedHelpers.callStaticMethod(aesUtilClazz, "getKey", false));
                LogUtil.e(decryptHex + "");
                if (decryptHex != null && decryptHex.contains("m3u8PlayUrl")) {
                    LogUtil.e("--Hook Video Play URL-- \n" + decryptHex);
                    JsonObject jsonObject = new JsonParser().parse(decryptHex).getAsJsonObject();
                    Gson gson = new Gson();
                    VideoInfoBean videoInfoBean = gson.fromJson(gson.toJson(jsonObject), VideoInfoBean.class);
                    String host = videoInfoBean.getDataBean().getM3u8PlayUrl();
                    $1080PPlayUrl = videoInfoBean.getDataBean().getM3u8Format().get_$1080P();
                    $720PPlayUrl = videoInfoBean.getDataBean().getM3u8Format().get_$720P();
                    $480PPlayUrl = videoInfoBean.getDataBean().getM3u8Format().get_$480P();
                    $360PPlayUrl = videoInfoBean.getDataBean().getM3u8Format().get_$360P();

                    $1080PPlayUrl = $1080PPlayUrl == null ? "无当前清晰度播放地址" : host + $1080PPlayUrl;
                    $720PPlayUrl = $720PPlayUrl == null ? "无当前清晰度播放地址" : host + $720PPlayUrl;
                    $480PPlayUrl = $480PPlayUrl == null ? "无当前清晰度播放地址" : host + $480PPlayUrl;
                    $360PPlayUrl = $360PPlayUrl == null ? "无当前清晰度播放地址" : host + $360PPlayUrl;

                    LogUtil.e("1  " + $1080PPlayUrl);
                    LogUtil.e("2  " + $720PPlayUrl);
                    LogUtil.e("3  " + $480PPlayUrl);
                    LogUtil.e("4  " + $360PPlayUrl);

                }

            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
//                LogUtil.e(param.args[0] + "--after");
            }
        });
    }

    /**
     * 解析视频真实地址(适用于麻花影视2.6.0后)
     * /api/app/video/ver2/user/clickPlayVideo_2_5/
     *
     * @param classLoader
     * @throws ClassNotFoundException
     */
    private void parseVideoUrl(ClassLoader classLoader) throws ClassNotFoundException {
//        final Class playerPresenterClazz = classLoader.loadClass(playerPresenterClassName);
        final Class videoAddressResponseClazz = classLoader.loadClass(videoAddressResponseClassName);
        final Class m3u8FormatBeanClazz = classLoader.loadClass(m3u8FormatBeanClassName);
        XposedHelpers.findAndHookMethod(playerPresenterClassName, classLoader, "a",
                videoAddressResponseClazz, int.class, Integer.class, String.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
//                        LogUtil.e(param.args[0] + "");
                        Object videoAddressResponseObject = param.args[0];
                        Method getM3u8PlayUrlMethod = XposedHelpers.findMethodBestMatch(videoAddressResponseClazz, "getM3u8PlayUrl");
                        String host = (String) getM3u8PlayUrlMethod.invoke(videoAddressResponseObject);

                        Method getM3u8FormatMethod = XposedHelpers.findMethodBestMatch(videoAddressResponseClazz, "getM3u8Format");
                        Object M3u8FormatObject = getM3u8FormatMethod.invoke(videoAddressResponseObject);
                        Method getM3u8FormatUrlMethod = XposedHelpers.findMethodBestMatch(m3u8FormatBeanClazz, "getM3u8Format", int.class);
                        $360PPlayUrl = (String) getM3u8FormatUrlMethod.invoke(M3u8FormatObject, 0);
                        $480PPlayUrl = (String) getM3u8FormatUrlMethod.invoke(M3u8FormatObject, 1);
                        $720PPlayUrl = (String) getM3u8FormatUrlMethod.invoke(M3u8FormatObject, 2);
                        $1080PPlayUrl = (String) getM3u8FormatUrlMethod.invoke(M3u8FormatObject, 3);

                        $1080PPlayUrl = $1080PPlayUrl == null ? "无当前清晰度播放地址" : host + $1080PPlayUrl;
                        $720PPlayUrl = $720PPlayUrl == null ? "无当前清晰度播放地址" : host + $720PPlayUrl;
                        $480PPlayUrl = $480PPlayUrl == null ? "无当前清晰度播放地址" : host + $480PPlayUrl;
                        $360PPlayUrl = $360PPlayUrl == null ? "无当前清晰度播放地址" : host + $360PPlayUrl;

                        LogUtil.e("1080  " + $1080PPlayUrl);
                        LogUtil.e("720  " + $720PPlayUrl);
                        LogUtil.e("480  " + $480PPlayUrl);
                        LogUtil.e("360  " + $360PPlayUrl);

                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
//                        Field videoAddressResponseField = XposedHelpers.findField(playerPresenterClazz, "L");
//                        videoAddressResponseField.setAccessible(true);
//                        LogUtil.e(videoAddressResponseField.get(param.thisObject) + "---");
                    }
                });
    }

    @Override
    public boolean isOpen() {
        return XPrefUtils.getPref().getBoolean("video_url", false);
    }
}