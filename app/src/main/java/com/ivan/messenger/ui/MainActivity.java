package com.ivan.messenger.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ivan.messenger.R;
import com.ivan.messenger.firebase.message.MessageSender;
import com.ivan.messenger.lib.utils.common.CommonUtils;
import com.ivan.messenger.ui.widget.fab.FloatingActionButton;

/**
 * Created by zhaoyifan on 17-9-8.
 */

public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private FloatingActionButton mFab;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        CommonUtils.safeStartIntent(context, intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFab = findViewById(R.id.fab_edit);
        mFab.setOnClickListener(this);
    }

    @Override
    protected int getFragmentContainerId() {
        return 0;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_edit:
                MessageSender sender = new MessageSender();
                sender.postMessage(FirebaseInstanceId.getInstance().getToken(), "Hello world");
                break;
        }
    }
}
