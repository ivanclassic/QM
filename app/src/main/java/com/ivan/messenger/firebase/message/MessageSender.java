package com.ivan.messenger.firebase.message;

import android.os.Handler;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.ivan.messenger.lib.BackgroundThread;
import com.ivan.messenger.lib.Env;
import com.ivan.messenger.lib.utils.ILog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by zhaoyifan on 17-9-25.
 */

public class MessageSender {
    private static final String TAG = "MessageSender";
    private static final int SOCKET_OPERATION_TIMEOUT = 5 * 1000;

    public static final String SENDER_URL = "https://fcm.googleapis.com/fcm/send";

    public void postMessage2(String to, String message) {
        ILog.d("MessagingService", "send message to: " + to);
        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        fm.send(new RemoteMessage.Builder("751182625945@gcm.googleapis.com")
                .setMessageId(Long.toString(System.currentTimeMillis()))
                .addData("my_message", "Hello World")
                .addData("my_action","SAY_HELLO")
                .build());
    }

    public void postMessage(final String to, final String message) {
        new Thread() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(SENDER_URL);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Authorization", "key=AIzaSyDtTf_6L020ooEAjjREWW6A8E-4s-iYfNY");
                    conn.setRequestMethod("POST");
                    conn.connect();

                    // HTTP request
                    JSONObject sendMsg = new JSONObject();
                    sendMsg.put("to", to);
                    JSONObject data = new JSONObject();
                    data.put("body", message);
                    data.put("send_time", System.currentTimeMillis());
                    sendMsg.put("data", data);

                    OutputStream os = conn.getOutputStream();
                    os.write(sendMsg.toString().getBytes("UTF-8"));
                    os.close();

                    int code = conn.getResponseCode();
                    String resMsg = conn.getResponseMessage();
                    // Read the response into a string
                    InputStream is = conn.getInputStream();
                    String responseString = new Scanner(is, "UTF-8").useDelimiter("\\A").next();
                    is.close();

                    // Parse the JSON string and return the notification key
                    JSONObject response = new JSONObject(responseString);
                    ILog.d(TAG, "@@@@@@@@@@ responseString: " + responseString);
                } catch (Throwable e) {
                    if (Env.DEBUG) {
                        e.printStackTrace();
                    }
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
        }.start();
//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//        BackgroundThread.post(new Runnable() {
//            @Override
//            public void run() {
//            }
//        });
    }
}
