package com.ivan.messenger.sp;

/**
 * Created by zhaoyifan on 17-8-22.
 */

public class KBaseConfigManager {

    public int getIntValue(String key, int defValue){
        return ServiceConfigManager.getInstance().getIntValue(key, defValue);
    }

    public void setIntValue(String key, int defValue){
        ServiceConfigManager.getInstance().setIntValue(key, defValue);
    }

    public boolean getBooleanValue(String key, boolean defValue){
        return ServiceConfigManager.getInstance().getBooleanValue(key, defValue);
    }

    public void setBooleanValue(String key, boolean defValue){
        ServiceConfigManager.getInstance().setBooleanValue(key, defValue);
    }

    public void setStringValue(String key, String defValue){
        ServiceConfigManager.getInstance().setStringValue(key, defValue);
    }

    public String getStringValue(String key, String defValue){
        return ServiceConfigManager.getInstance().getStringValue(key, defValue);
    }

    public void setLongValue(String key, long time){
        ServiceConfigManager.getInstance().setLongValue(key,time);
    }

    public long getLongValue(String key, long def){
        return ServiceConfigManager.getInstance().getLongValue(key,def);
    }

    public void removeKey(String key){
        ServiceConfigManager.getInstance().removeKey(key);
    }
}
