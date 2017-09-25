package com.ivan.messenger.lib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ivan.messenger.lib.BackgroundThread;
import com.ivan.messenger.lib.live.PermanentService;
import com.ivan.messenger.lib.utils.ProcessUtil;
import com.ivan.messenger.lib.utils.common.CommonUtils;

/**
 * Created by zhaoyifan on 17-9-25.
 */

public class UserPresentReceiver extends BroadcastReceiver {
    private static final String TAG = "UserPresentReceiver";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        BackgroundThread.post(new Runnable() {
            @Override
            public void run() {
                if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
                    if (!ProcessUtil.isServiceRunning(context, PermanentService.class.getName())) {
                        CommonUtils.startPermanentService(context);
                    }
                }
            }
        });
    }
}
