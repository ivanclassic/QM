package com.ivan.messenger.lib.utils;

import android.util.Log;

import com.ivan.messenger.lib.Env;

/**
 * Created by zhaoyifan on 17-7-12.
 */

public class ILog {

    public static void d(String tag, String message) {
        if (Env.DEBUG) {
            Log.d(tag, message);
            System.out.println(tag + ": " + message);
        }
    }

    public static void e(String tag, String message) {
        if (Env.DEBUG) {
            Log.e(tag, message);
            System.out.println(tag + ": " + message);
        }
    }

    public static void e(String tag, String message, Throwable e) {
        if (Env.DEBUG) {
            Log.e(tag, message, e);
            System.out.println(tag + ": " + message + " e: " + e.getMessage());
        }
    }
}
