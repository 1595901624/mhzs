package com.lhy.xposed.mhzs.activity;

import android.graphics.Color;
import android.view.MenuItem;

import com.lhy.xposed.mhzs.fragment.AdSettingFragment;
import com.lhy.xposed.mhzs.fragment.TabSettingFragment;

import androidx.appcompat.widget.Toolbar;

public class TabSettingActivity extends BaseActivity {
    @Override
    protected void initToolbar(Toolbar mToolbar) {
        mToolbar.setTitle("广告净化");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        switchFragment(new TabSettingFragment());
    }

}
