package com.ivan.messenger.lib.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.text.TextUtils;

import com.ivan.messenger.lib.Env;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ProcessUtil {

    public static String getProcessName(Context context) {
        // Try first way: read from /proc/self/cmdline, FORBIDDEN in Android 5.x
        BufferedReader reader = null;
        try {
            final File cmdFile = new File("/proc/self/cmdline");
            if (!cmdFile.isDirectory()) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(cmdFile)));
                String procName = reader.readLine().trim();
                if (!TextUtils.isEmpty(procName)) {
                    ILog.d("Application", "get process=" + procName);
                    return procName;
                }
            }
        } catch (Throwable e) {
            if (Env.DEBUG) {
                e.printStackTrace();
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    if (Env.DEBUG) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // Try second way: read by getRunningAppProcesses()
        try {
            final int myPid = android.os.Process.myPid();
            final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            final List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                if (procInfo.pid == myPid) {
                    return procInfo.processName;
                }
            }
        } catch (Throwable e) {
            if (Env.DEBUG) {
                e.printStackTrace();
            }
        }
        // Try default way: it seems all processes has a SAME process name
        return context.getApplicationInfo().processName;
    }

    public static boolean isServiceRunning(Context context, String serviceName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<RunningServiceInfo> lists = am.getRunningServices(30);
            for (RunningServiceInfo info : lists) {
                if (info.service.getClassName().equals(serviceName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isProessRunning(Context context, String proessName) {
        if (TextUtils.isEmpty(proessName)) {
            return false;
        }
        // 过滤自己
        String myPName = getProcessName(context);
        if (myPName.equalsIgnoreCase(proessName)) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<RunningAppProcessInfo> infos = am.getRunningAppProcesses();
            for (RunningAppProcessInfo rapi : infos) {
                if (rapi.processName.equalsIgnoreCase(proessName)) {
                    return true;
                }
            }
        }
        return false;
    }
}