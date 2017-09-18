package com.ivan.messenger.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ivan.messenger.R;
import com.ivan.messenger.lib.utils.common.CommonUtils;

/**
 * Created by zhaoyifan on 17-9-8.
 */

public class MainActivity extends BaseFragmentActivity {
    private static final String TAG = "MainActivity";

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        CommonUtils.safeStartIntent(context, intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected int getFragmentContainerId() {
        return 0;
    }
}
