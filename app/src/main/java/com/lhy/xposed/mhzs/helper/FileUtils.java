package com.lhy.xposed.mhzs.helper;

import android.content.Context;
import android.os.Environment;

import com.lhy.xposed.mhzs.BuildConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by zpp0196 on 2019/2/9.
 */

public class FileUtils {
    private static final String FILE_PREF_NAME = BuildConfig.APPLICATION_ID + "_preferences.xml";

    public static boolean copyFile(File srcFile, File targetFile) {
        FileInputStream ins = null;
        FileOutputStream out = null;
        try {
            if (targetFile.exists()) {
                targetFile.delete();
            }
            File targetParent = targetFile.getParentFile();
            if (!targetParent.exists()) {
                targetParent.mkdirs();
            }
            targetFile.createNewFile();
            ins = new FileInputStream(srcFile);
            out = new FileOutputStream(targetFile);
            byte[] b = new byte[1024];
            int n;
            while ((n = ins.read(b)) != -1) {
                out.write(b, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ins != null) {
                    ins.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static File getDataDir(Context context) {
        return new File(context.getApplicationInfo().dataDir);
    }

    public static File getPrefDir(Context context) {
        return new File(getDataDir(context), "shared_prefs");
    }

    public static File getDefaultPrefFile(Context context) {
        return new File(getPrefDir(context), FILE_PREF_NAME);
    }

    public static File getBackupPrefsFile() {
        return new File(getBackupDir(), FILE_PREF_NAME);
    }

    private static File getBackupDir() {
        return new File(Environment.getExternalStorageDirectory(), "QQPurify");
    }
}
