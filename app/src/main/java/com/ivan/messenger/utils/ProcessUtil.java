package com.ivan.messenger.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

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
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
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
            e.printStackTrace();
        }
        // Try default way: it seems all processes has a SAME process name
        return context.getApplicationInfo().processName;
    }
}