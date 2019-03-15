package com.lhy.xposed.mhzs.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.lhy.xposed.mhzs.BuildConfig;
import com.lhy.xposed.mhzs.R;
import com.lhy.xposed.mhzs.fragment.SettingFragment;
import com.lhy.xposed.mhzs.helper.FileUtils;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceFragmentCompat;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
@Deprecated
public class SettingsActivity extends AppCompatPreferenceActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

//    static {
//        /* Shell.Config methods shall be called before any shell is created
//         * This is the reason why you should call it in a static block
//         * The followings are some examples, check Javadoc for more details */
//        Shell.Config.setFlags(Shell.FLAG_REDIRECT_STDERR);
//        Shell.Config.verboseLogging(BuildConfig.DEBUG);
//        Shell.Config.setTimeout(10);
//    }

    public final int REQUEST_BACKUP = 1;
    public final int REQUEST_RESTORE = 2;
    public File defaultPref = null;
    public File backupPref = null;

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources()
                        .getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
               Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    public boolean isModuleActive() {
        // VirtualXposed 在某些机型上hook短方法有问题，这里认为添加日志增大方法长度确保能hook成功。
        Log.i("fake", "isModuleActive");
        return false;
    }

    public boolean isExpModuleActive() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defaultPref = FileUtils.getDefaultPrefFile(this);
        backupPref = FileUtils.getBackupPrefsFile();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String versionCodeKey = "module_versionCode";
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi (Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragmentCompat.class.getName()
                       .equals(fragmentName) || SettingFragment.class.getName()
//                       .equals(fragmentName) || ConversationPreferenceFragment.class.getName()
//                       .equals(fragmentName) || ContactsPreferenceFragment.class.getName()
//                       .equals(fragmentName) || LebaPreferenceFragment.class.getName()
//                       .equals(fragmentName) || ChatPreferenceFragment.class.getName()
//                       .equals(fragmentName) || TroopPreferenceFragment.class.getName()
//                       .equals(fragmentName) || ExtensionPreferenceFragment.class.getName()
//                       .equals(fragmentName) || SettingPreferenceFragment.class.getName()
//                       .equals(fragmentName) || AboutPreferenceFragment.class.getName()
                       .equals(fragmentName);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.menu_reboot_qq) {
//            rebootQQ();
//        } else if (item.getItemId() == R.id.menu_exit) {
//            setWorldReadable();
//            System.exit(0);
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setWorldReadable();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && requestCode == REQUEST_BACKUP &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            backupPref();
        } else if (grantResults.length > 0 && requestCode == REQUEST_RESTORE &&
                   grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            restoreBackup();
        }
    }

    public void backupPref() {
//        if (FileUtils.copyFile(defaultPref, backupPref)) {
//            Toast.makeText(this, getString(R.string.tip_setting_backup_finish_success, backupPref.getAbsolutePath()), Toast.LENGTH_LONG)
//                    .show();
//        }
    }

    public void restoreBackup() {
//        if (!backupPref.exists()) {
//            Toast.makeText(this, R.string.tip_setting_restore_file_not_find, Toast.LENGTH_LONG)
//                    .show();
//        } else if (FileUtils.copyFile(backupPref, defaultPref)) {
//            showRestartDialog();
//        }
    }


//    public void openAlipay() {
//        String qrcode = "FKX03149H8YOUWESHOCEC6";
//        try {
//            getPackageManager().getPackageInfo(Constants.PACKAGE_NAME_ALIPAY, PackageManager.GET_ACTIVITIES);
//            openUrl("alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=https://qr.alipay.com/" +
//                    qrcode + "%3F_s%3Dweb-other&_t=");
//        } catch (PackageManager.NameNotFoundException e) {
//            openUrl("https://mobilecodec.alipay.com/client_download.htm?qrcode=" + qrcode);
//        }
//    }

    public void openUrl(String url) {
        Uri content_url = Uri.parse(url);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(content_url);
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings ({"deprecation", "ResultOfMethodCallIgnored"})
    @SuppressLint ({"SetWorldReadable", "WorldReadableFiles"})
    public void setWorldReadable() {
        if (FileUtils.getDefaultPrefFile(this)
                .exists()) {
            for (File file : new File[]{FileUtils.getDataDir(this), FileUtils.getPrefDir(this), FileUtils.getDefaultPrefFile(this)}) {
                file.setReadable(true, false);
                file.setExecutable(true, false);
            }
        }
    }
}
