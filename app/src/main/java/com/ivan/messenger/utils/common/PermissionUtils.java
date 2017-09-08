package com.ivan.messenger.utils.common;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.ivan.messenger.IMessengerApplication;
import com.ivan.messenger.utils.ILog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyifan on 17-9-1.
 */

public class PermissionUtils {

    @NonNull
    public static List<String> getNecessaryPermissions(@NonNull String[] permissions) {
        List<String> needRequestPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (!hasPermission(permission)) {
                needRequestPermissions.add(permission);
            }
        }
        return needRequestPermissions;
    }

    public static boolean hasPermission(String permission) {
        return ActivityCompat.checkSelfPermission(IMessengerApplication.getInstance(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissions(Activity activity, List<String> np, int code) {
        String[] ps = new String[np.size()];
        np.toArray(ps);
        requestPermissions(activity, ps, code);
    }

    public static void requestPermissions(Activity activity, String[] ps, int code) {
        ActivityCompat.requestPermissions(activity, ps, code);
    }
}