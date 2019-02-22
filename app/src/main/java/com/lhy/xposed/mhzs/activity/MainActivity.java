package com.lhy.xposed.mhzs.activity;

import android.graphics.Color;

import com.lhy.xposed.mhzs.fragment.SettingFragment;

import androidx.appcompat.widget.Toolbar;


public class MainActivity extends BaseActivity {

    @Override
    protected void initToolbar(Toolbar mToolbar) {
        mToolbar.setTitle("麻花影视助手");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void initData() {
        switchFragment(new SettingFragment());
    }

}
