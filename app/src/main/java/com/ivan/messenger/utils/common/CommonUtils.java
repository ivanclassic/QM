package com.ivan.messenger.utils.common;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.ivan.messenger.IMessengerApplication;
import com.ivan.messenger.sp.KUserConfigManager;

/**
 * Created by zhaoyifan on 17-8-22.
 */

public class CommonUtils {

    @SuppressLint({"MissingPermission", "HardwareIds" })
    public static String getPhoneNumber() {
        String phoneNumber = KUserConfigManager.getInstance().getStoragedPhoneNumber();
        if (phoneNumber != null) {
            return phoneNumber;
        } else {
            Context context = IMessengerApplication.getInstance().getApplicationContext();
            TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (checkPermissions(Manifest.permission.READ_PHONE_STATE)) {
                throw new RuntimeException("try to get line1 phone number without permission granted");
            }
            if (tMgr != null) {
                phoneNumber = tMgr.getLine1Number();
            }
            KUserConfigManager.getInstance().setStoragedPhoneNumber(phoneNumber);
            return phoneNumber;
        }
    }

    public static boolean checkPermissions(String permission) {
        return ActivityCompat.checkSelfPermission(IMessengerApplication.getInstance(), permission) == PackageManager.PERMISSION_GRANTED;
    }
}
