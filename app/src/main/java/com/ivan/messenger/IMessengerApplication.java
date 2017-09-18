package com.ivan.messenger;

import android.app.Application;
import android.content.res.Configuration;

import com.ivan.messenger.lib.utils.ProcessUtil;

import java.io.File;

/**
 * Created by zhaoyifan on 17-7-12.
 */

public class IMessengerApplication extends Application {

    private static IMessengerApplication theApp;

    public static IMessengerApplication getInstance() {
        return theApp;
    }

    private String mProcessName = "";

    public IMessengerApplication() {
        theApp = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mProcessName = ProcessUtil.getProcessName(this);
        RuntimeCheck.init(mProcessName);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public File getExternalFilesRootDir() {
        try {
            return getExternalFilesDir(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
