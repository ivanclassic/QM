package com.ivan.messenger;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Environment;

import java.io.File;

public class Env {
    public static final boolean DEBUG = BuildConfig.DEBUG;

    public static String getPkgName(Context context) {
        return context.getPackageName();
    }

    public static String getPkgName() {
        return IMessengerApplication.getInstance().getPackageName();
    }

    static String getVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return info.versionName + "(" + info.versionCode + ")";
        } catch (/*NameNotFoundException*/Exception e) {
            return null;
        }
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return info.versionCode;
        } catch (/*NameNotFoundException*/Exception e) {
            return -1;
        }
    }

    private static String getRealVersionName() {
        return VERSION_NAME;
    }

    public static int getRealVersionCode() {
        return VERSION_CODE;
    }

     static String getRealVersionNameAndCode() {
        return getRealVersionName() + "(" + getRealVersionCode() + ")";
    }

    public static String getExternalStorageDirectoryx() {
        File apkCacheFile = IMessengerApplication.getInstance().getExternalFilesRootDir();

        String apkCacheDir = null;
        if (apkCacheFile != null) {
            apkCacheDir = apkCacheFile.getAbsolutePath();
        }

        try {
            if (apkCacheDir == null) {
                apkCacheDir = new File(Environment.getExternalStorageDirectory(), "imessenger").getAbsolutePath();
            }

            final File file = new File(apkCacheDir + "/");
            if (!file.exists()) {
                file.mkdirs(); // 如果没有路径，创建之
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return apkCacheDir;
    }

    public static final int VERSION_CODE = 45040207;
    public static final String VERSION_NAME = "4.5.4";
}
