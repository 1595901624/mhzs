package com.lhy.xposed.mhzs.fragment;

import android.os.Bundle;

import com.lhy.xposed.mhzs.R;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class AdSettingFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_mhzs_ad);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        return super.onPreferenceTreeClick(preference);
    }
}
