package com.lhy.xposed.mhzs.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.lhy.xposed.mhzs.fragment.SettingFragment;
import com.lhy.xposed.mhzs.helper.HYHelper;
import com.lhy.xposed.mhzs.helper.ToastUtils;
import com.lhy.xposed.mhzs.permission.PermissionHelper;
import com.lhy.xposed.mhzs.permission.PermissionInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends BaseActivity implements PermissionInterface {
    private PermissionHelper mPermissionHelper;

    @Override
    protected void initToolbar(Toolbar mToolbar) {
        mToolbar.setTitle("麻花影视助手 " + HYHelper.getPackageName(this));
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
        //初始化并发起权限申请
//        mPermissionHelper = new PermissionHelper(this, this);
//        mPermissionHelper.requestPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mPermissionHelper.requestPermissionsResult(requestCode, permissions, grantResults)) {
            //权限请求结果，并已经处理了该回调
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public int getPermissionsRequestCode() {
        return 19680;
    }

    @Override
    public String[] getPermissions() {
        return new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
    }

    @Override
    public void requestPermissionsSuccess() {

    }

    @Override
    public void requestPermissionsFail() {
        ToastUtils.toast(this, "应用需要权限才能正常运行！");
        finish();
    }
}
