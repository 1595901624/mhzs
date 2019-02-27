package com.lhy.xposed.mhzs.plugin;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lhy.xposed.mhzs.helper.LogUtil;
import com.lhy.xposed.mhzs.bean.VideoInfoBean;
import com.lhy.xposed.mhzs.helper.ToastUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * 获取视频真实地址 1.1
 *
 * @author lhy
 * @time 2019年2月18日11:58:37
 * @update 2019年2月27日21:15:48
 * 在PlayerActivity中添加按钮获取地址：
 */
public class VideoURLPlugin implements IPlugin {
    private final String resultStrHandleSubscriberClassName = "com.mh.movie.core.mvp.model.a.b";
    private final String aesUtilClassName = "com.mh.movie.core.mvp.ui.utils.AesUtil";
    private final String playerActvityClassName = "com.mh.movie.core.mvp.ui.activity.player.PlayerActivity";
    private final String r$idClassName = "com.mh.movie.core.R$id";

    private String $1080PPlayUrl;
    private String $720PPlayUrl;
    private String $480PPlayUrl;
    private String $360PPlayUrl;

    @Override
    public void run(ClassLoader classLoader) throws Throwable {
        try {
            getVideoURL(classLoader);
            addExtraLayout(classLoader);
        } catch (ClassNotFoundException e) {
            LogUtil.e("AesUtil ClassNotFoundException!");
            XposedBridge.log(e);
        } catch (Exception e) {
            LogUtil.e("VideoURLPlugin Unknown Error!");
            XposedBridge.log(e);
        }
    }

    private void addExtraLayout(ClassLoader classLoader) throws ClassNotFoundException {
        Class r$idClazz = classLoader.loadClass(r$idClassName);
        final int clPlayerRootId = XposedHelpers.getStaticIntField(r$idClazz, "cl_player_root");
        final int rlPlayerIntroduceId = XposedHelpers.getStaticIntField(r$idClazz, "rl_player_introduce");
        XposedHelpers.findAndHookMethod(playerActvityClassName, classLoader, "Z", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                final Activity activity = (Activity) param.thisObject;
                RelativeLayout rootView = activity.findViewById(rlPlayerIntroduceId);
//                TextView child = new TextView(activity);
//                child.setTextSize(20);
//                child.setText("布局入侵！！！！");
//                rootView.addView(child);
                Button button = new Button(activity);
                button.setText("480P");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ($480PPlayUrl != null)
                            ToastUtils.toast(activity, $480PPlayUrl + "--");
                        else
                            ToastUtils.toast(activity, "无法获取到播放地址！");
                    }
                });
                // 定义LayoutParam
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
//                params.rightMargin = 30;
                rootView.addView(button, params);

                LogUtil.e("布局入侵！！！！");
            }
        });
    }

    /**
     * 解析地址
     *
     * @param classLoader
     * @throws ClassNotFoundException
     */

    private void getVideoURL(final ClassLoader classLoader) throws ClassNotFoundException {
        final Class aesUtilClazz = classLoader.loadClass(aesUtilClassName);
        XposedHelpers.findAndHookMethod(resultStrHandleSubscriberClassName, classLoader, "a", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);

                String decryptHex = (String) XposedHelpers.callStaticMethod(aesUtilClazz, "decryptHex", param.args[0],
                        XposedHelpers.callStaticMethod(aesUtilClazz, "getKey", false));
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

    @Override
    public boolean isOpen() {
        return true;
    }
}