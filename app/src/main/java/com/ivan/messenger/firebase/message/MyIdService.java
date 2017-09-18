package com.ivan.messenger.firebase.message;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.ivan.messenger.lib.utils.ILog;

/**
 * Created by zhaoyifan on 17-6-13.
 */

public class MyIdService extends FirebaseInstanceIdService {
    private static final String TAG = "MyIdService";

    private TokenRefreshListener mListener;

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        ILog.d(TAG, "Refreshed token: " + refreshedToken);
//        sendRegistrationToServer(refreshedToken);
        if (mListener != null) {
            mListener.onTokenRefresh();
        }
    }

    public void registerListener(TokenRefreshListener listener) {
        mListener = listener;
    }

    public void unRegisterListener() {
        mListener = null;
    }

    public static interface TokenRefreshListener {
        public void onTokenRefresh();
    }
}
