package com.ivan.messenger.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.ivan.messenger.IMessengerApplication;
import com.ivan.messenger.RuntimeCheck;
import com.ivan.messenger.sp.provider.ConfigProvider;

/**
 * Created by zhaoyifan on 17-7-12.
 */

public class ServiceConfigManager implements IServiceConfig {

    private SharedPreferences mShardPreferences = null;

    private static class InnerConfigManager {
        private static final ServiceConfigManager instance = new ServiceConfigManager(IMessengerApplication.getInstance());
    }

    public static ServiceConfigManager getInstance() {
        return InnerConfigManager.instance;
    }

    private ServiceConfigManager(Context context) {
        String spName = context.getPackageName() + "_preferences";
        mShardPreferences = IMessengerApplication.getInstance().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    private SharedPreferences getSharedPreference() {
        RuntimeCheck.checkServiceProcess();
        return mShardPreferences;
    }

    @Override
    public long getLongValue(String key, long defValue) {
        if (RuntimeCheck.isServiceProcess()) {
            return getSharedPreference().getLong(key, defValue);
        } else {
            return ConfigProvider.getLongValue(key, defValue);
        }
    }

    @Override
    public boolean getBooleanValue(String key, boolean defValue) {
        if (RuntimeCheck.isServiceProcess()) {
            return getSharedPreference().getBoolean(key, defValue);
        } else {
            return ConfigProvider.getBooleanValue(key, defValue);
        }
    }

    @Override
    public int getIntValue(String key, int defValue) {
        if (RuntimeCheck.isServiceProcess()) {
            return getSharedPreference().getInt(key, defValue);
        } else {
            return ConfigProvider.getIntValue(key, defValue);
        }
    }

    @Override
    public String getStringValue(String key, String defValue) {
        if (RuntimeCheck.isServiceProcess()) {
            return getSharedPreference().getString(key, defValue);
        } else {
            return ConfigProvider.getStringValue(key, defValue);
        }
    }

    @Override
    public void setBooleanValue(String key, boolean value) {
        if (RuntimeCheck.isServiceProcess()) {
            SharedPreferences.Editor editor = getSharedPreference().edit();
            editor.putBoolean(key, value);
            editor.apply();
        } else {
            ConfigProvider.setBooleanValue(key, value);
        }
    }

    @Override
    public void setLongValue(String key, long value) {
        if (RuntimeCheck.isServiceProcess()) {
            SharedPreferences.Editor editor = getSharedPreference().edit();
            editor.putLong(key, value);
            editor.apply();
        } else {
            ConfigProvider.setLongValue(key, value);
        }
    }

    @Override
    public void setIntValue(String key, int value) {
        if (RuntimeCheck.isServiceProcess()) {
            SharedPreferences.Editor editor = getSharedPreference().edit();
            editor.putInt(key, value);
            editor.apply();
        } else {
            ConfigProvider.setIntValue(key, value);
        }
    }

    @Override
    public void setStringValue(String key, String value) {
        if (RuntimeCheck.isServiceProcess()) {
            SharedPreferences.Editor editor = getSharedPreference().edit();
            editor.putString(key, value);
            editor.apply();
        } else {
            ConfigProvider.setStringValue(key, value);
        }
    }

    @Override
    public void removeKey(String key) {
        if (RuntimeCheck.isServiceProcess()){
            SharedPreferences.Editor editor = getSharedPreference().edit();
            editor.remove(key);
            editor.apply();
        } else {
            ConfigProvider.removeKey(key);
        }
    }

    @Override
    public boolean hasKey(String key) {
        if (RuntimeCheck.isServiceProcess()) {
            return getSharedPreference().contains(key);
        } else {
            return ConfigProvider.contains(key);
        }
    }
}
