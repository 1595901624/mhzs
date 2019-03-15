package com.lhy.xposed.mhzs.plugin;

import com.lhy.xposed.mhzs.helper.Constant;
import com.lhy.xposed.mhzs.helper.LogUtil;
import com.lhy.xposed.mhzs.helper.XPrefUtils;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * 强制微信分享成功
 * <p>
 * （不完美）
 *
 * @author lhy
 * @time 2019年2月17日12:18:26
 * <p>
 * 2019年3月12日11:07:58 开启测试
 */
public class WechatSharePlugin implements IPlugin {

//    private final String detailResponseClassName = "com.mh.movie.core.mvp.model.entity.response.DetailResponse$VideoListBean";
//    private final String playerPresenterClassName = "com.mh.movie.core.mvp.presenter.player.PlayerPresenter";
//    private final String wxShareClassName = "com.amahua.share.e";
//    private final String wxShareBroadReceiverClassName = "com.amahua.share.e$a";
//    private final String playerActivityClassName = "com.mh.movie.core.mvp.ui.activity.player.PlayerActivity";

//    private final String wXEntryActivityClassName = "com.amahua.ompimdrt.wxapi.WXEntryActivity";
    private final String baseRespClassName = "com.tencent.mm.opensdk.modelbase.BaseResp";

    @Override
    public void run(final ClassLoader classLoader) throws Throwable {

        try {
            watchHotMovie(classLoader);
        } catch (ClassNotFoundException e) {
            LogUtil.e("BaseResp NotFound!");
            XposedBridge.log(e);
        } catch (Exception e) {
            LogUtil.e("watchHotMovie Unknown Error");
            XposedBridge.log(e);
        }


    }

    private void watchHotMovie(final ClassLoader classLoader) throws ClassNotFoundException {
        final Class<?> baseRespClazz = classLoader.loadClass(baseRespClassName);

        /**
         * 方法1：
         * 改变分享结果
         */
        XposedHelpers.findAndHookMethod(Constant.act.$WXEntryActivity, classLoader, "onResp", baseRespClazz, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

//                Constructor constructors[] = baseRespClazz.getConstructors();
//                for (Constructor constructor : constructors) {
//                    LogUtil.e(constructor + "--");
//                }
//
//                Field fields[] = baseRespClazz.getFields();
//                for (Field field : fields) {
//                    LogUtil.e(field + "--");
//                }

                Field field = baseRespClazz.getField("errCode");
                field.setAccessible(true);
                field.setInt(param.args[0], 0);
                LogUtil.e("errCode = " + field.getInt(param.args[0]));

                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });

        /**
         * 无效的方法
         */
//        XposedHelpers.findAndHookMethod(playerActivityClassName, classLoader, "v", new XC_MethodReplacement() {
//            @Override
//            protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
//
////                Constructor constructor = XposedHelpers.findConstructorBestMatch(baseRespClazz);
////                Object baseRespObject = constructor.newInstance();
//////                Object baseRespObject = baseRespClazz.newInstance();
////                Field errCodeField = baseRespClazz.getField("errCode");
////                errCodeField.setAccessible(true);
////                errCodeField.setInt(baseRespObject, 0);
////
////                Class[] parameterTypes = {baseRespClazz};
////                Constructor constructor2 = responseClazz.getConstructor(parameterTypes);
////                Object[] parameters = {baseRespObject};
////                Object responseObject = constructor2.newInstance(parameters);
//
//                Object responseObject = responseClazz.newInstance();
//                Field errCodeField = responseClazz.getField("a");
//                                errCodeField.setAccessible(true);
//                errCodeField.setInt(responseObject, 0);
//                Parcelable parcelable = (Parcelable) responseClazz.cast(responseObject);
//
//                Activity mActivity = (Activity) methodHookParam.thisObject;
//                Intent intent = new Intent("action_wx_share_response");
//                intent.putExtra("result", parcelable);
//                mActivity.sendBroadcast(intent);
//                return null;
//            }
//        });
    }


    @Override
    public boolean isOpen() {
        return XPrefUtils.getPref().getBoolean("wechat_share", true);
    }
}
