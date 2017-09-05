package com.ivan.messenger;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.ivan.messenger.sp.KUserConfigManager;
import com.ivan.messenger.ui.SignupActivity;
import com.ivan.messenger.utils.ILog;
import com.ivan.messenger.utils.common.CommonUtils;
import com.ivan.messenger.utils.common.PermissionUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "test.MainActivity";

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
        // TODO： 0.1.2.3
        // 0. 取得电话号码
        mMyNumber = KUserConfigManager.getInstance().getStoragedPhoneNumber();
        if (TextUtils.isEmpty(mMyNumber)) {
            tryToGetPhonenumber();
        } else {
            handleLogin();
        }
        // 1. 欢迎动画，只要进程被回收，重新进来就应该执行

//        List<String> np = PermissionUtils.getNecessaryPermissions(permissions);
//        if (np.isEmpty()) {
//            mMyNumber = CommonUtils.getPhoneNumber();
//        } else {
//            if (PermissionUtils.hasPermission(android.Manifest.permission.READ_PHONE_STATE)) {
//                mMyNumber = CommonUtils.getPhoneNumber();
//            }
//            ILog.d(TAG, "request permission: " + np.size());
//            PermissionUtils.requestPermissions(this, np, REQUEST_CODE_PERMISSION);
//        }
    }

    private void tryToGetPhonenumber() {
        if (PermissionUtils.hasPermission(android.Manifest.permission.READ_PHONE_STATE)) {
            mMyNumber = CommonUtils.getPhoneNumber();
            if (!TextUtils.isEmpty(mMyNumber)) {
                handleLogin();
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_PHONE_STATE},
                    REQUEST_CODE_PERMISSION);
        }
    }

    private void handleLogin() {
        if (!TextUtils.isEmpty(mMyNumber)) {
            boolean registed = checkIfRegisted(mMyNumber);
            if (registed) {
                // 3. 如果注册过，直接进入主页
            } else {
                // 2. 如果没有注册过，进入SignUpActivity进行注册
                SignupActivity.start(this);
            }
        }
    }

    private boolean checkIfRegisted(@NonNull String number) {
        boolean result = false;

        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ILog.d(TAG, "onActivityResult");
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                ILog.d(TAG, "permission requested");
                mMyNumber = CommonUtils.getPhoneNumber();
                if (!TextUtils.isEmpty(mMyNumber)) {
                    handleLogin();
                }
                break;
        }
    }
}
