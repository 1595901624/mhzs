package com.lhy.xposed.mhzs.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import androidx.preference.SwitchPreferenceCompat;

import com.lhy.xposed.mhzs.R;
import com.lhy.xposed.mhzs.activity.AdActivity;
import com.lhy.xposed.mhzs.helper.ToastUtils;

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
                break;
            case "ad_set":
                startActivity(new Intent(getActivity(), AdActivity.class));
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

}
