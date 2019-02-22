package com.lhy.xposed.mhzs.activity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.lhy.xposed.mhzs.R;
import com.lhy.xposed.mhzs.helper.FileUtils;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public abstract class BaseActivity extends AppCompatActivity {
    private FragmentTransaction fragmentTransaction;
    private Fragment currentFragment;
    private Toolbar mToolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        initToolbar(mToolbar);
        initView();
        initData();
    }

    protected abstract void initToolbar(Toolbar mToolbar);

    protected abstract void initView();

    protected abstract void initData();


    protected void switchFragment(Fragment targetFragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            if (currentFragment != null) {
                fragmentTransaction.hide(currentFragment);
            }
            fragmentTransaction.add(R.id.container, targetFragment)
                    .commit();
        } else {
            fragmentTransaction.hide(currentFragment)
                    .show(targetFragment)
                    .commit();
        }
        //更改当前的fragment所指向的值
        currentFragment = targetFragment;
    }

    @Override
    protected void onPause() {
        super.onPause();
        setWorldReadable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setWorldReadable();
    }

    /**
     * 设置Pref为可读
     */
    @SuppressWarnings({"deprecation", "ResultOfMethodCallIgnored"})
    @SuppressLint({"SetWorldReadable", "WorldReadableFiles"})
    public void setWorldReadable() {
        if (FileUtils.getDefaultPrefFile(this)
                .exists()) {
            for (File file : new File[]{FileUtils.getDataDir(this), FileUtils.getPrefDir(this), FileUtils.getDefaultPrefFile(this)}) {
                file.setReadable(true, false);
                file.setExecutable(true, false);
            }
        }
    }

    /*************************************************************
     * EXP/VXP SUPPORT START
     * ***********************************************************/

    protected boolean isModuleActive() {
        // VirtualXposed 在某些机型上hook短方法有问题，这里认为添加日志增大方法长度确保能hook成功。
        Log.i("fake", "isModuleActive");
        return false;
    }

    protected boolean isExpModuleActive() {
        boolean isActive = false;
        try {
            ContentResolver contentResolver = getContentResolver();
            Uri uri = Uri.parse("content://me.weishu.exposed.CP/");
            Bundle result = contentResolver.call(uri, "active", null, null);
            if (result == null) {
                return false;
            }
            isActive = result.getBoolean("active", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isActive;
    }

    /*************************************************************
     * EXP/VXP SUPPORT END
     * ***********************************************************/
}
