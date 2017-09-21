package com.ivan.messenger.lib.live;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.IBinder;

import com.ivan.messenger.EntryActivity;
import com.ivan.messenger.R;
import com.ivan.messenger.lib.BackgroundThread;
import com.ivan.messenger.lib.KFinalValues;
import com.ivan.messenger.lib.utils.ILog;
import com.ivan.messenger.ui.MainActivity;

import static com.ivan.messenger.lib.KFinalValues.NOTIFICATION_KEEP_ALIVE;


public class PermanentService extends Service {
    private static final String TAG = "PermanentService";
    private static final long GCM_REG_CHECK_INTERVAL = 5000;

    private Thread mAsyncInitThread;

    /**
     * 增加个异步初始化方法
     */
    private void asynchronousInit() {
        if (mAsyncInitThread == null) {
            mAsyncInitThread = new Thread() {
                @Override
                public void run() {
                    synchronized (this) {
                        try {
                            // 延迟五分钟报活，以便能正常统计到GP的渠道号
                            wait(KFinalValues.DELAY_REPORT_TIME);
                        } catch (Exception e) {
                            ILog.e(TAG, "asynchronousInit", e);
                        }
                    }
                    mAsyncInitThread = null;
                }
            };
            mAsyncInitThread.start();
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        ILog.d(TAG, "onCreate");
        initReceiver();
        timerToRegGCM();
        asynchronousInit();
    }

    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mScreenObserver, filter);
    }

    @Override
    public void onDestroy() {
        ILog.d(TAG, "onDestroy");
        stopForeground(true);
        if (mScreenObserver != null) {
            unregisterReceiver(mScreenObserver);
        }
        final Thread thread = mAsyncInitThread;
        if (thread != null) {
            synchronized (thread) {
                try {
                    thread.notifyAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ILog.d(TAG, "onStartCommand");
        Intent nfIntent = new Intent(this, EntryActivity.class);
        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0))
                .setContentText(this.getText(R.string.notification_summary_keep_alive))
                .setContentTitle(this.getText(R.string.notification_title_keep_alive))
                .setSmallIcon(R.drawable.ic_swap_vertical_circle_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_touch_app_black_24dp))
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(false)
                .setOngoing(true)
                .build();
        startForeground(NOTIFICATION_KEEP_ALIVE, notification);
        return Service.START_STICKY;
    }

    private BinderContainer mBinderContainer = new BinderContainer();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinderContainer;
    }

    private void timerToRegGCM() {
        Context context = getApplicationContext();
        if (null == context) {
            return;
        }
        try {
            AlarmManager alm;
            alm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent serviceIntent = new Intent(context, PermanentService.class);
            PendingIntent pi = PendingIntent.getService(context, 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (alm != null) {
                alm.cancel(pi);
                alm.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + GCM_REG_CHECK_INTERVAL,
                        GCM_REG_CHECK_INTERVAL, pi);
            }
        } catch (SecurityException ex) {
            ILog.e(TAG, "SecurityException", ex);
        } catch (Exception e) {
            ILog.e(TAG, "Exception", e);
        }
    }

    private BroadcastReceiver mScreenObserver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                handleScreenOff();
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                handleActiveWhenUserPresent();
            } else if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(action)) {
                handleAirPlayMode();
            } else if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                handleNetWorkChangeReceiver();
            }
        }
    };

    private void handleScreenOff() {
    }

    private void handleActiveWhenUserPresent() {
    }

    private void handleAirPlayMode() {
    }

    private void handleNetWorkChangeReceiver() {
        BackgroundThread.removeTask(mNetWorkChangeRunnable);
        BackgroundThread.postDelayed(mNetWorkChangeRunnable, 6000);
    }

    private Runnable mNetWorkChangeRunnable = new Runnable() {
        @Override
        public void run() {
        }
    };
}