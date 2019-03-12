package com.lhy.xposed.mhzs.activity;

import android.graphics.Color;

import com.lhy.xposed.mhzs.fragment.ExpSettingFragment;

import androidx.appcompat.widget.Toolbar;

public class ExpSettingActivity extends BaseActivity {
    @Override
    protected void initToolbar(Toolbar mToolbar) {
        mToolbar.setTitle("实验性功能");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        switchFragment(new ExpSettingFragment());
    }
}
