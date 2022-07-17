package com.example.esa.messaging;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.esa.Dashboard;
import com.example.esa.DashboardNurse;
import com.example.esa.DashboardSurgeon;
import com.example.esa.PatientList;
import com.example.esa.PatientListAnes;
import com.example.esa.R;
import com.example.esa.SharedPrefManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class FirebaseService extends FirebaseMessagingService {

    private final String CHANNEL_ID_1 = "channel_id";

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String role = SharedPrefManager.getInstance(this).getUserRole();

        // Intent intent = new Intent(this, ProfileActivity.class);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = new Random().nextInt();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(manager);
        }
        //change by follow ada role of users
        if (role.equals("Nurse") | role.equals("Houseman")) {
            Intent intent = new Intent(this, DashboardNurse.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent intent1 = PendingIntent.getActivities(this, 0, new Intent[]{intent}, PendingIntent.FLAG_ONE_SHOT);
            //PendingIntent intent1 = PendingIntent.getActivities(this,0,new Intent[]{},PendingIntent.FLAG_ONE_SHOT);

            Notification notification;

            notification = new NotificationCompat.Builder(this, CHANNEL_ID_1)
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setContentText(remoteMessage.getData().get("message"))
                    .setSmallIcon(R.drawable.notification)
                    .setAutoCancel(true)
                    .setContentIntent(intent1)
                    .build();

            manager.notify(notificationId, notification);

        }


        else if (role.equals("Anaesthetist") ) {
            Intent intent = new Intent(this, Dashboard.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent intent1 = PendingIntent.getActivities(this, 0, new Intent[]{intent}, PendingIntent.FLAG_ONE_SHOT);
            //PendingIntent intent1 = PendingIntent.getActivities(this,0,new Intent[]{},PendingIntent.FLAG_ONE_SHOT);

            Notification notification;

            notification = new NotificationCompat.Builder(this, CHANNEL_ID_1)
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setContentText(remoteMessage.getData().get("message"))
                    .setSmallIcon(R.drawable.notification)
                    .setAutoCancel(true)
                    .setContentIntent(intent1)
                    .build();

            manager.notify(notificationId, notification);

        }

        else if (role.equals("Surgeon")) {
            Intent intent = new Intent(this, DashboardSurgeon.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent intent1 = PendingIntent.getActivities(this, 0, new Intent[]{intent}, PendingIntent.FLAG_ONE_SHOT);
            //PendingIntent intent1 = PendingIntent.getActivities(this,0,new Intent[]{},PendingIntent.FLAG_ONE_SHOT);

            Notification notification;

            notification = new NotificationCompat.Builder(this, CHANNEL_ID_1)
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setContentText(remoteMessage.getData().get("message"))
                    .setSmallIcon(R.drawable.notification)
                    .setAutoCancel(true)
                    .setContentIntent(intent1)
                    .build();

            manager.notify(notificationId, notification);

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(NotificationManager manager) {

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID_1, "channelName", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("My Description");
        channel.enableLights(true);
        channel.setLightColor(Color.WHITE);

        manager.createNotificationChannel(channel);

    }
}