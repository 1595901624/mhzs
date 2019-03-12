package com.lhy.xposed.mhzs.fragment;

import android.os.Bundle;

import com.lhy.xposed.mhzs.R;

import androidx.preference.Preference;

/**
 * 实验性功能
 *
 * @author lhy
 * @time 2019年3月12日10:56:55
 */
public class ExpSettingFragment extends BasePreferenceFragment {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.pref_mhzs_exp);
        setWorldReadable();
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        return super.onPreferenceTreeClick(preference);

    }
}
