package nna.ca.ha.focusec.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import nna.ca.ha.focusec.MainActivity;
import nna.ca.ha.focusec.R;

public class ScreenService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "focusec";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "focusec",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setShowBadge(false);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("몇초집중함")
                    .setContentText("몇 초 집중했는지 감시 중.")
                    .setSmallIcon(R.drawable.ic_center_focus_weak_black_24dp)

                    .build();

            startForeground(2, notification);
        }

    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        try {

            String getState = intent.getExtras().getString("state");
            assert getState != null;
            switch (getState) {
                case "on":
                    startId = 1;
                    break;
                case "off":
                    startId = 0;
                    stopForeground(true);
                    break;
                default:
                    startId = 0;
                    break;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            startId=0;
        }

        if (startId == 1) {
            final ScreenBroadCast receiver = new ScreenBroadCast();
            final IntentFilter filter = new IntentFilter("android.intent.action.SCREEN_ON");
            filter.addAction("android.intent.action.SCREEN_OFF");
            registerReceiver(receiver, filter);
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("onDestory() 실행", "서비스 파괴");

    }

}
