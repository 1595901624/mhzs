package com.lhy.xposed.mhzs.fragment;

import android.os.Bundle;

import com.lhy.xposed.mhzs.R;

import androidx.preference.PreferenceFragmentCompat;

public class TabSettingFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_mhzs_tab);
    }
}
