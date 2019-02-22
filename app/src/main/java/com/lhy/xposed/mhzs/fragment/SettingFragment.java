package com.lhy.xposed.mhzs.fragment;

import android.content.Intent;
import android.didikee.donate.AlipayDonate;
import android.didikee.donate.WeiXinDonate;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import androidx.preference.SwitchPreferenceCompat;

import com.lhy.xposed.mhzs.R;
import com.lhy.xposed.mhzs.activity.AdSettingActivity;
import com.lhy.xposed.mhzs.activity.TabSettingActivity;
import com.lhy.xposed.mhzs.helper.ToastUtils;

import java.io.File;
import java.io.InputStream;

public class SettingFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.pref_mhzs);
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
            case "donate_alipay":
                donateAlipay();
                return true;
            case "donate_wx":
                donateWeixin();
                return true;
            default:
                break;
        }
        return false;
    }

    private void globalSet(Preference preference) {
        SwitchPreferenceCompat switchPreferenceCompat = findPreference("global_set");
        PreferenceGroup pcDetailPreferenceGroup = findPreference("pc_detail");
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
        }
    }

    /**
     * 微信捐献
     */
    private void donateWeixin() {
        InputStream weixinQrIs = getResources().openRawResource(R.raw.wx_donate);
        String qrPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "AndroidDonateSample" + File.separator +
                "wx_donate.png";
        WeiXinDonate.saveDonateQrImage2SDCard(qrPath, BitmapFactory.decodeStream(weixinQrIs));
        WeiXinDonate.donateViaWeiXin(getActivity(), qrPath);
    }

}