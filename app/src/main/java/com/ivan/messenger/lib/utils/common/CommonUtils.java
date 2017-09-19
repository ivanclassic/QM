package com.ivan.messenger.lib.utils.common;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.ivan.messenger.IMessengerApplication;
import com.ivan.messenger.lib.live.PermanentService;
import com.ivan.messenger.lib.sp.KUserConfigManager;
import com.ivan.messenger.lib.utils.ILog;

/**
 * Created by zhaoyifan on 17-8-22.
 */

public class CommonUtils {
    private static final String TAG = "test.CommonUtils";

    @SuppressLint({"MissingPermission", "HardwareIds" })
    public static String getPhoneNumber() {
        String phoneNumber = KUserConfigManager.getInstance().getStoragedPhoneNumber();
        if (phoneNumber != null) {
            return phoneNumber;
        } else {
            Context context = IMessengerApplication.getInstance().getApplicationContext();
            TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (!PermissionUtils.hasPermission(Manifest.permission.READ_PHONE_STATE)) {
                throw new RuntimeException("try to get line1 phone number without permission granted");
            }
            if (tMgr != null) {
                phoneNumber = tMgr.getLine1Number();
            }
            KUserConfigManager.getInstance().setStoragedPhoneNumber(phoneNumber);
            return phoneNumber;
        }
    }

    public static boolean safeStartIntent(Context context, Intent intent) {
        if (context == null || intent == null){
            return false;
        }
        boolean bResult = true;
        try {
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        } catch (Exception e) {
            bResult = false;
            ILog.e(TAG, "打开Intent失败:", e);
        }
        return bResult;
    }

    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // 启动一个服务
    public static void startPermanentService(Context context) {
        if (null == context) {
            return;
        }
        Intent serviceIntent = new Intent(context, PermanentService.class);
        try {
            if (null == context.startService(serviceIntent)) {
                ILog.e("Servive", "start service fail");
            }
        } catch (Exception e) {
            ILog.e("Servive", "startPermanentService", e);
        }
    }
}
