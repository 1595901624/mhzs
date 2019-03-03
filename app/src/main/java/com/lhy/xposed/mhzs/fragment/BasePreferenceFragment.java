package com.lhy.xposed.mhzs.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.lhy.xposed.mhzs.helper.FileUtils;

import java.io.File;

import androidx.preference.PreferenceFragmentCompat;

public class BasePreferenceFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    }

    /**
     * 设置Pref为可读
     */
    @SuppressWarnings({"deprecation", "ResultOfMethodCallIgnored"})
    @SuppressLint({"SetWorldReadable", "WorldReadableFiles"})
    protected void setWorldReadable() {
        if (FileUtils.getDefaultPrefFile(getActivity())
                .exists()) {
            for (File file : new File[]{FileUtils.getDataDir(getActivity()), FileUtils.getPrefDir(getActivity()), FileUtils.getDefaultPrefFile(getActivity())}) {
                file.setReadable(true, false);
                file.setExecutable(true, false);
            }
        }
    }
}
