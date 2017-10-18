package com.ivan.messenger.firebase.message;

import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ivan.messenger.lib.utils.ILog;

/**
 * Created by zhaoyifan on 17-6-13.
 */

public class MessagingService extends FirebaseMessagingService {
    private static final String TAG = "MessagingService";

    private Handler mHandler = new Handler();

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        ILog.d(TAG, "From: " + remoteMessage.getFrom());
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            ILog.d(TAG, "Message data payload: " + remoteMessage.getData());
            scheduleJob();
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            ILog.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), remoteMessage.getFrom() + "\n"
                        + remoteMessage.getData() + "\n"
                        + remoteMessage.getNotification().getBody(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
        ILog.d(TAG, "onMessageSent: " + s);
        Toast.makeText(getApplicationContext(), "success: " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
        ILog.d(TAG, "onSendError: " + s);
        Toast.makeText(getApplicationContext(), "error: " + s, Toast.LENGTH_SHORT).show();
    }

    private void scheduleJob() {
        // TODO: useing FirebaseJobDispatcher to handle job
    }
}
