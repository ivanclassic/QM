package com.ivan.messenger.utils;

import android.util.Log;

import com.ivan.messenger.BuildConfig;

/**
 * Created by zhaoyifan on 17-7-12.
 */

public class ILog {
    private static final boolean DEBUG = BuildConfig.DEBUG;

    public static void d(String tag, String message) {
        if (DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void e(String tag, String message, Throwable e) {
        if (DEBUG) {
            Log.e(tag, message, e);
        }
    }
}
