package com.lhy.xposed.mhzs.fragment;

import android.os.Bundle;

import com.lhy.xposed.mhzs.R;
import com.lhy.xposed.mhzs.activity.BaseActivity;

import androidx.preference.PreferenceFragmentCompat;

public class TabSettingFragment extends BasePreferenceFragment {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_mhzs_tab);
        setWorldReadable();
    }
}
