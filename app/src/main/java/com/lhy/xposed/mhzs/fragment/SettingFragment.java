package com.lhy.xposed.mhzs.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.didikee.donate.AlipayDonate;
import android.didikee.donate.WeiXinDonate;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import androidx.preference.SwitchPreferenceCompat;

import com.lhy.xposed.mhzs.R;
import com.lhy.xposed.mhzs.activity.AdSettingActivity;
import com.lhy.xposed.mhzs.activity.ExpSettingActivity;
import com.lhy.xposed.mhzs.activity.HelpActivity;
import com.lhy.xposed.mhzs.activity.TabSettingActivity;
import com.lhy.xposed.mhzs.helper.ToastUtils;

import java.io.File;
import java.io.InputStream;

public class SettingFragment extends BasePreferenceFragment {
    private PreferenceGroup pcDetailPreferenceGroup;
    private SwitchPreferenceCompat switchPreferenceCompat;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.pref_mhzs);
        setWorldReadable();
        switchPreferenceCompat = findPreference("global_set");
        pcDetailPreferenceGroup = findPreference("pc_detail");

        SharedPreferences sp = getPreferenceManager().getSharedPreferences();
        pcDetailPreferenceGroup.setVisible(sp.getBoolean("global_set", false));


        // TODO: 2019/3/14 0014 检查模块运行状态1/3
//        if (!isModuleActive() && !isExpModuleActive()) {
//            pcDetailPreferenceGroup.setVisible(false);
//            switchPreferenceCompat.setChecked(false);
//        }
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        switch (preference.getKey()) {
            case "global_set":
                globalSet(preference);
                return true;
            case "ad_set":
                startActivity(new Intent(getActivity(), AdSettingActivity.class));
                return true;
            case "tab_set":
                startActivity(new Intent(getActivity(), TabSettingActivity.class));
                return true;
            case "help":
                startActivity(new Intent(getActivity(), HelpActivity.class));
                return true;
            case "github":
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/1595901624/mhzs")));
                return true;
            case "donate_alipay":
                donateAlipay();
                return true;
            case "lucky_alipay":
                getAlipayLucky();
                return true;
            case "donate_wx":
                donateWeixin();
                return true;
            case "exp_set":
                startActivity(new Intent(getActivity(), ExpSettingActivity.class));
                return true;
            default:
                break;
        }
        return false;
    }

    /**
     * 领红包
     */
    @Deprecated
    private void getAlipayLucky() {
        String luckyCode = "c1x08425objyhrkgjby6f26";
        boolean hasInstalledAlipayClient = AlipayDonate.hasInstalledAlipayClient(getActivity());
        if (hasInstalledAlipayClient) {
            AlipayDonate.startAlipayClient(getActivity(), luckyCode);
        } else {
            ToastUtils.toast(getActivity(), "当前设备未安装支付宝！");
        }
    }


    private void globalSet(Preference preference) {
        // TODO: 2019/3/14 0014 检查模块运行状态2/3
//        if (switchPreferenceCompat.isChecked() && !isModuleActive() && !isExpModuleActive()) {
//            switchPreferenceCompat.setChecked(false);
//            pcDetailPreferenceGroup.setVisible(false);
//            ToastUtils.toast(getActivity(), "请在Xposed框架中激活!");
//            return;
//        }

        if (switchPreferenceCompat.isChecked()) {
            pcDetailPreferenceGroup.setVisible(true);
            ToastUtils.toast(getActivity(), "麻花助手已经开启！");
        } else {
            pcDetailPreferenceGroup.setVisible(false);
            ToastUtils.toast(getActivity(), "麻花助手已经关闭！");
        }

    }

    /**
     * 支付宝捐献
     */
    private void donateAlipay() {
        String payCode = "fkx09681rnsrxn9nog4sy2c";
        boolean hasInstalledAlipayClient = AlipayDonate.hasInstalledAlipayClient(getActivity());
        if (hasInstalledAlipayClient) {
            AlipayDonate.startAlipayClient(getActivity(), payCode);
        } else {
            ToastUtils.toast(getActivity(), "当前设备未安装支付宝！");
        }
    }

    /**
     * 微信捐献
     */
    private void donateWeixin() {
        InputStream weixinQrIs = getResources().openRawResource(R.raw.wechatc);
        String qrPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "AndroidDonateSample" + File.separator +
                "wechatc.jpg";
        WeiXinDonate.saveDonateQrImage2SDCard(qrPath, BitmapFactory.decodeStream(weixinQrIs));
        WeiXinDonate.donateViaWeiXin(getActivity(), qrPath);
    }

    /**********************************************************************************************
     *
     *                                      以下方法勿动
     *
     **********************************************************************************************/


    public boolean isModuleActive() {
        // Tai-Chi 在某些机型上hook短方法有问题，这里认为添加日志增大方法长度确保能hook成功。
        Log.i("fake", "isModuleActive");
        return false;
    }

    public boolean isExpModuleActive() {
        boolean isActive = false;
        try {
            ContentResolver contentResolver = getActivity().getContentResolver();
            Uri uri = Uri.parse("content://me.weishu.exposed.CP/");
            Bundle result = contentResolver.call(uri, "active", null, null);
            if (result == null) {
                return false;
            }
            isActive = result.getBoolean("active", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isActive;
    }
}
