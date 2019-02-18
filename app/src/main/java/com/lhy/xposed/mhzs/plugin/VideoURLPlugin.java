package com.lhy.xposed.mhzs.plugin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lhy.xposed.mhzs.helper.LogUtil;
import com.lhy.xposed.mhzs.bean.VideoInfoBean;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * 获取视频真实地址
 *
 * @author lhy
 * @time 2019年2月18日11:58:37
 */
public class VideoURLPlugin implements IPlugin {
    private final String resultStrHandleSubscriberClassName = "com.mh.movie.core.mvp.model.a.b";
    private final String aesUtilClassName = "com.mh.movie.core.mvp.ui.utils.AesUtil";

    @Override
    public void run(ClassLoader classLoader) throws Throwable {
        try {
            getVideoURL(classLoader);
        } catch (ClassNotFoundException e) {
            LogUtil.e("AesUtil ClassNotFoundException!");
            XposedBridge.log(e);
        } catch (Exception e) {
            LogUtil.e("VideoURLPlugin Unknown Error!");
            XposedBridge.log(e);
        }
    }

    /**
     * 解析地址
     *
     * @param classLoader
     * @throws ClassNotFoundException
     */
    private void getVideoURL(ClassLoader classLoader) throws ClassNotFoundException {
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
                    String $1080PPlayUrl = videoInfoBean.getDataBean().getM3u8Format().get_$1080P();
                    String $720PPlayUrl = videoInfoBean.getDataBean().getM3u8Format().get_$720P();
                    String $480PPlayUrl = videoInfoBean.getDataBean().getM3u8Format().get_$480P();
                    String $360PPlayUrl = videoInfoBean.getDataBean().getM3u8Format().get_$360P();

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
}