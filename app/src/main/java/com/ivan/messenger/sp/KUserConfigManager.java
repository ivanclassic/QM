package com.ivan.messenger.sp;

/**
 * Created by zhaoyifan on 17-8-22.
 */

public class KUserConfigManager extends KBaseConfigManager {

    private static final String SPKEY_MY_PHONE_NUMBER = "spkey_my_phone_number_0000";

    private static KUserConfigManager ourInstance = new KUserConfigManager();
    public static KUserConfigManager getInstance() {
        return ourInstance;
    }

    private KUserConfigManager() {
        super();
    }

    public String getStoragedPhoneNumber() {
        return getStringValue(SPKEY_MY_PHONE_NUMBER, null);
    }

    public void setStoragedPhoneNumber(String number) {
        setStringValue(SPKEY_MY_PHONE_NUMBER, number);
    }
}
