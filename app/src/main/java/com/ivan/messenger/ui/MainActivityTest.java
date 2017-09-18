package com.ivan.messenger.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ivan.messenger.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivityTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        findViewById(R.id.send_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                FirebaseMessaging fm = FirebaseMessaging.getInstance();
////                fm.send(new RemoteMessage.Builder("838511304531@gcm.googleapis.com")
////                        .setMessageId(Long.toString(System.currentTimeMillis()))
////                        .addData("my_message", "Hello World")
////                        .addData("my_action","SAY_HELLO")
////                        .build());
//                new Thread() {
//                    @Override
//                    public void run() {
//                        postMessage();
//                    }
//                }.start();
//            }
//        });
    }

    /**
     { "data": {
     "score": "5x1",
     "time": "15:10"
     },
     "to" : "eNDSFJNEo9E:APA91bHelF-kUHiQIcSMSkfXckxJOmsI_Qdz05cajnNrV9hQh8lJt4qDc92o4dpblKoucet7xw1bs6JX0h3et2yY2Eaf9ttHMPnKxxJs3ttJ-okg5sYiHFlgbMknJ20oyMXuQ6UWy9Jm"
     }
     */
    private void postMessage() {
        try {
            String jsonString = "{\"data\":{\n" +
                    "     \"score\":\"5x1\",\n" +
                    "     \"time\":\"15:10\"\n" +
                    "     },\n" +
                    "     \"to\":\"eNDSFJNEo9E:APA91bHelF-kUHiQIcSMSkfXckxJOmsI_Qdz05cajnNrV9hQh8lJt4qDc92o4dpblKoucet7xw1bs6JX0h3et2yY2Eaf9ttHMPnKxxJs3ttJ-okg5sYiHFlgbMknJ20oyMXuQ6UWy9Jm\"\n" +
                    "     }";
            JSONObject json = new JSONObject(jsonString);
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "key=AAAAwzsrH1M:APA91bGFNbNugCK92HtVzUMTej3Hdk2Po4LntznTbrcObVUnlzODieEe2A_Iuif0K1ju2z_EEOJwKvsuMswxohkNw2cxuDg93VcG-JLdhYJXahi3qTc4Sj8AdAQoWOuX6za5UARNRH_Y");

            connection.setUseCaches(false);
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("POST");
            byte[] bytes = json.toString().getBytes();

            connection.setDoOutput(true);
            connection.setFixedLengthStreamingMode(bytes.length);

            OutputStream out = connection.getOutputStream();
            out.write(bytes);
            out.flush();
            out.close();

            int status = connection.getResponseCode();

            if (200 <= status && status <= 299) {
                String readLine = null;
                StringBuilder res = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((readLine = br.readLine()) != null) {
                    res.append(readLine);
                }

                if (res.length() != 0) {
                    Log.d("IVANDEBUG", "@@@@@@@@@@2 res: " + res.toString());
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }
}
