package com.ivan.messenger.sp;

public interface IServiceConfig {

    long getLongValue(String key, long defValue);

    boolean getBooleanValue(String key, boolean defValue);

    int getIntValue(String key, int defValue);

    String getStringValue(String key, String defValue);

    void setBooleanValue(String key, boolean value);

    void setLongValue(String key, long value);

    void setIntValue(String key, int value);

    void setStringValue(String key, String value);

    void removeKey(String key);

    boolean hasKey(String key);
}
