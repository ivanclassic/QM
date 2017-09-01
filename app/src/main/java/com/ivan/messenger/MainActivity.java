package com.ivan.messenger;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ivan.messenger.utils.common.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 0;
    private static final String[] permissions = new String[] {
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.RECEIVE_BOOT_COMPLETED,
            android.Manifest.permission.GET_ACCOUNTS,
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.VIBRATE
    };

    private String mMyNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (CommonUtils.checkPermissions(android.Manifest.permission.READ_PHONE_STATE)) {
            mMyNumber = CommonUtils.getPhoneNumber();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_PHONE_STATE},
                    REQUEST_CODE_PERMISSION);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                mMyNumber = CommonUtils.getPhoneNumber();
                break;
        }
    }


    private boolean checkAndRequestPermissions() {
        List<String> needRequestPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                needRequestPermissions.add(permission);
            }
        }
        if (!needRequestPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this, (String[]) needRequestPermissions.toArray(), 0);
            return false;
        } else {
            return true;
        }
    }
}
