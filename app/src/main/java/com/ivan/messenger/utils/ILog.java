package com.ivan.messenger.utils;

import android.util.Log;

import com.ivan.messenger.BuildConfig;
import com.ivan.messenger.Env;

/**
 * Created by zhaoyifan on 17-7-12.
 */

public class ILog {

    public static void d(String tag, String message) {
        if (Env.DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (Env.DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void e(String tag, String message, Throwable e) {
        if (Env.DEBUG) {
            Log.e(tag, message, e);
        }
    }
}
