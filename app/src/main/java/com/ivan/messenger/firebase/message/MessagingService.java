package com.ivan.messenger.firebase.message;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ivan.messenger.utils.ILog;

/**
 * Created by zhaoyifan on 17-6-13.
 */

public class MessagingService extends FirebaseMessagingService {
    private static final String TAG = "MessagingService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
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
    }

    private void scheduleJob() {
        // TODO: useing FirebaseJobDispatcher to handle job
    }
}
