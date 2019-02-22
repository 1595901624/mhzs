package com.lhy.xposed.mhzs.activity;

import android.graphics.Color;
import android.view.MenuItem;

import com.lhy.xposed.mhzs.fragment.AdSettingFragment;

import androidx.appcompat.widget.Toolbar;

public class AdSettingActivity extends BaseActivity {
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
        switchFragment(new AdSettingFragment());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

