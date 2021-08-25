package com.example.promena.voter.Service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.promena.voter.Common.Constants;
import com.example.promena.voter.Common.Preferences;
import com.example.promena.voter.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String NOTIFICATION_CHANNEL_ID = "Testing2";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData() !=null){


            sendNotification(remoteMessage);

        }
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        Map<String,String> data = remoteMessage.getData();
        String title = data.get("title");
        String content = data.get("content");
        Preferences pref = new Preferences(MyFirebaseMessagingService.this);
        pref.set(Constants.nn_count,"1");
        pref.commit();

        Uri defaultsounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            @SuppressLint("WrongConstant")

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID
                    ,"TestingNotification"
                    ,NotificationManager.IMPORTANCE_MAX);
            notificationChannel.setDescription("Testing on 8 and higher");
            notificationChannel.enableLights(true);
            //  notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            //notificationChannel.setVibrationPattern(new long[](0,1000,500,1000));
            NotificationManager notificationManager1 = getSystemService(NotificationManager.class);
            notificationManager1.createNotificationChannel(notificationChannel);

        }else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext());
            notificationBuilder.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(title)
                    .setContentText(content).setContentInfo("info");
            notificationManager.notify(1, notificationBuilder.build());

        }
    }
}
