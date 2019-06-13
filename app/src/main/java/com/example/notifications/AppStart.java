package com.example.notifications;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class AppStart extends Application {
    public static final String CHANNEL_SportsID = "Sports Channel";
    public static final String CHANNEL_NewsID = "News Channel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel sportsCH = new NotificationChannel(
                    CHANNEL_SportsID,
                    "Sports Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            sportsCH.setDescription("For sending Sports Notifications");//=========

            NotificationChannel newsCH = new NotificationChannel(
                    CHANNEL_NewsID,
                    "News Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            newsCH.setDescription("For sending News Notifications");//===========

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(sportsCH);
            manager.createNotificationChannel(newsCH);
        }
    }
}