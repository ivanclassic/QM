package com.ivan.messenger.sp.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.ivan.messenger.IMessengerApplication;
import com.ivan.messenger.RuntimeCheck;
import com.ivan.messenger.sp.ServiceConfigManager;
import com.ivan.messenger.utils.ILog;

public class ConfigProvider extends ContentProvider {
    private static final String TAG = "ConfigProvider";

    public static final String AUTHORITY = "com.ivan.messenger.sp.provider";
    public static final Uri CONFIG_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private static final int LENGTH_CONTENT_URI = CONFIG_CONTENT_URI.toString().length() + 1;

    @Override
    public boolean onCreate() {
        RuntimeCheck.setServiceProcess();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return null;
    }


    private static String EXTRA_TYPE = "type";
    private static String EXTRA_KEY = "key";
    private static String EXTRA_VALUE = "value";

    private static final int TYPE_BOOLEAN = 1;
    private static final int TYPE_INT = 2;
    private static final int TYPE_LONG = 3;
    private static final int TYPE_STRING = 4;


    private static ContentResolver getCr() {
        return IMessengerApplication.getInstance().getContentResolver();
    }

    public static boolean contains(String key) {
        ContentValues contetvalues = new ContentValues();
        contetvalues.put(EXTRA_TYPE, TYPE_LONG);
        contetvalues.put(EXTRA_KEY, key);
        contetvalues.put(EXTRA_VALUE, 0);
        Uri result = safeInsert(contetvalues);
        return result != null;
    }

    public static void setBooleanValue(String key, boolean value) {
        ContentValues contetvalues = new ContentValues();

        contetvalues.put(EXTRA_TYPE, TYPE_BOOLEAN);
        contetvalues.put(EXTRA_KEY, key);
        contetvalues.put(EXTRA_VALUE, value);

        safeUpdate(contetvalues);
    }

    public static void setLongValue(String key, long value) {
        ContentValues contetvalues = new ContentValues();

        contetvalues.put(EXTRA_TYPE, TYPE_LONG);
        contetvalues.put(EXTRA_KEY, key);
        contetvalues.put(EXTRA_VALUE, value);

        safeUpdate(contetvalues);
    }

    private static void safeUpdate(ContentValues contetvalues) {
        try {
            getCr().update(CONFIG_CONTENT_URI, contetvalues, null, null);
        } catch (Exception e) {
            ILog.e(TAG, "safeUpdate() : " + e.getMessage());
        }
    }

    public static void setIntValue(String key, int value) {
        ContentValues contetvalues = new ContentValues();
        contetvalues.put(EXTRA_TYPE, TYPE_INT);
        contetvalues.put(EXTRA_KEY, key);
        contetvalues.put(EXTRA_VALUE, value);

        safeUpdate(contetvalues);
    }

    public static void setStringValue(String key, String value) {
        ContentValues contetvalues = new ContentValues();
        contetvalues.put(EXTRA_TYPE, TYPE_STRING);
        contetvalues.put(EXTRA_KEY, key);
        contetvalues.put(EXTRA_VALUE, value);

        safeUpdate(contetvalues);
    }

    public static long getLongValue(String key, long defValue) {
        ContentValues contetvalues = new ContentValues();
        contetvalues.put(EXTRA_TYPE, TYPE_LONG);
        contetvalues.put(EXTRA_KEY, key);
        contetvalues.put(EXTRA_VALUE, defValue);
        Uri result = safeInsert(contetvalues);

        if (result == null)
            return defValue;
        String rr = result.toString().substring(LENGTH_CONTENT_URI);
        if (TextUtils.isEmpty(rr)) {
            return defValue;
        }
        return Long.parseLong(rr);
    }

    public static boolean getBooleanValue(String key, boolean defValue) {
        ContentValues contetvalues = new ContentValues();
        contetvalues.put(EXTRA_TYPE, TYPE_BOOLEAN);
        contetvalues.put(EXTRA_KEY, key);
        contetvalues.put(EXTRA_VALUE, defValue);
        Uri result = safeInsert(contetvalues);

        if (result == null)
            return defValue;
        return Boolean.parseBoolean(result.toString().substring(LENGTH_CONTENT_URI));
    }

    private static Uri safeInsert(ContentValues contetvalues) {
        Uri result = null;
        try {
            result = getCr().insert(CONFIG_CONTENT_URI, contetvalues);
        } catch (Exception e) {
            e.printStackTrace();
            ILog.e(TAG, "safeInsert() : " + e.getMessage());
        }
        return result;
    }

    public static int getIntValue(String key, int defValue) {
        ContentValues contetvalues = new ContentValues();
        contetvalues.put(EXTRA_TYPE, TYPE_INT);
        contetvalues.put(EXTRA_KEY, key);
        contetvalues.put(EXTRA_VALUE, defValue);
        Uri result = safeInsert(contetvalues);

        if (result == null)
            return defValue;
        String rr = result.toString().substring(LENGTH_CONTENT_URI);
        if (TextUtils.isEmpty(rr)) {
            return defValue;
        }
        return Integer.parseInt(rr);
    }

    public static String getStringValue(String key, String defValue) {
        ContentValues contetvalues = new ContentValues();
        contetvalues.put(EXTRA_TYPE, TYPE_STRING);
        contetvalues.put(EXTRA_KEY, key);
        contetvalues.put(EXTRA_VALUE, defValue);
        Uri result = safeInsert(contetvalues);

        if (result == null)
            return defValue;

        String res = String.valueOf(result.toString().substring(LENGTH_CONTENT_URI));
        return TextUtils.isEmpty(res) ? defValue : res;
    }

    public static void removeKey(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXTRA_KEY, key);
        try {
            getCr().delete(CONFIG_CONTENT_URI, EXTRA_KEY + "=?", new String[]{key});
        } catch (Exception e) {
            ILog.e(TAG, "removeKey() : " + e.getMessage());
        }
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        RuntimeCheck.checkServiceProcess();
        int nType = values.getAsInteger(EXTRA_TYPE);
        String res = null;
        if (nType == TYPE_BOOLEAN) {
            boolean b = ServiceConfigManager.getInstance().getBooleanValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsBoolean(EXTRA_VALUE));
            res = String.valueOf(b);
        } else if (nType == TYPE_STRING) {
            res = ServiceConfigManager.getInstance().getStringValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsString(EXTRA_VALUE));
            if (res == null) {
                res = "";
            }
        } else if (nType == TYPE_INT) {
            int i = ServiceConfigManager.getInstance().getIntValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsInteger(EXTRA_VALUE));
            res = String.valueOf(i);
        } else if (nType == TYPE_LONG) {
            long l = ServiceConfigManager.getInstance().getLongValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsLong(EXTRA_VALUE));
            res = String.valueOf(l);
        }

        return Uri.parse(CONFIG_CONTENT_URI.toString() + "/" + res);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        RuntimeCheck.checkServiceProcess();

        int nType = values.getAsInteger(EXTRA_TYPE);
        if (nType == TYPE_BOOLEAN) {
            ServiceConfigManager.getInstance().setBooleanValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsBoolean(EXTRA_VALUE));
        } else if (nType == TYPE_STRING) {
            ServiceConfigManager.getInstance().setStringValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsString(EXTRA_VALUE));
        } else if (nType == TYPE_INT) {
            ServiceConfigManager.getInstance().setIntValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsInteger(EXTRA_VALUE));
        } else if (nType == TYPE_LONG) {
            ServiceConfigManager.getInstance().setLongValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsLong(EXTRA_VALUE));
        }
        return 1;
    }

}
