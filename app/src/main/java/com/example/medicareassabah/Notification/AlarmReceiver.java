package com.example.medicareassabah.Notification;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.medicareassabah.AlertService;
import com.example.medicareassabah.R;
import com.example.medicareassabah.UserHomeActivity;


public class AlarmReceiver extends BroadcastReceiver {
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";
    String name;
    String notification;

    @Override
    public void onReceive(Context context, Intent intent) {
//        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        name = intent.getStringExtra("cid");
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.varathan_bgm);
        mediaPlayer.setLooping(false); // Set looping
        mediaPlayer.setVolume(100, 100);
        Intent notificationIntent = new Intent(context.getApplicationContext(), UserHomeActivity.class);
        notificationIntent.putExtra("NotificationMessage", name);
        PendingIntent intent1 = PendingIntent.getActivity(context.getApplicationContext(), 0,
                notificationIntent, 0);



        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, default_notification_channel_id);


        mBuilder.setContentTitle("Remainder:Time to take your " + name + " tablet");
        mBuilder.setContentText(name);
        mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(intent1);
//        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel);


        }
        assert mNotificationManager != null;
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
    mNotificationManager.notify(73195, mBuilder.build());

    }
}

