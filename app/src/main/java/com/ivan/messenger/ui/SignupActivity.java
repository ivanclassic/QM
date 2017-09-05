package com.ivan.messenger.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ivan.messenger.utils.common.CommonUtils;

/**
 * 注册用Activity，上报到runtime database。
 * 使用电话号码进行注册，不可更改。
 *
 * Created by zhaoyifan on 17-9-1.
 */

public class SignupActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, SignupActivity.class);
        CommonUtils.safeStartIntent(context, intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
