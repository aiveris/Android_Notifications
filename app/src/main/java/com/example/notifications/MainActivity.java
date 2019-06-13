package com.example.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.notifications.R;


public class MainActivity extends AppCompatActivity {
    NotificationManagerCompat notificationManager;
    EditText title;
    EditText msg;
    Bitmap largeIcon;
    String longText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);

        title = findViewById(R.id.edit_text_title);
        msg = findViewById(R.id.edit_text_message);

        largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.fed);
        longText = "Roger Federer holds several ATP records and is considered to be one of the greatest tennis players of all time." +
                " The Swiss player has proved his dominance on court with 20 Grand Slam titles and 101 career ATP titles. ";
    }

    public void sendOnSportsChannel(View v) {
        String t = title.getText().toString();
        String m = msg.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, AppStart.CHANNEL_SportsID)
                .setSmallIcon(R.drawable.noti)
                .setContentTitle(t)
                .setContentText(m)
                .setColor(Color.GREEN)
                .setDefaults(Notification.DEFAULT_SOUND)//not needed +Android v8(Oreo)... picks sound from Priority
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);//will get replaced
    }

    public void sendOnNewsChannel(View v) {
        String t = title.getText().toString();
        String m = msg.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, AppStart.CHANNEL_NewsID)
                .setSmallIcon(R.drawable.noti)
                .setContentTitle(t)
                .setContentText(m)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(2, notification);//will get replaced
    }

    public void OpenActivity_Intent(View v){
        String t = title.getText().toString();
        String m = msg.getText().toString();

//for Opening an activity
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//multiple instances without this
        PendingIntent openActivityIntent = PendingIntent.getActivity(this,0, i, 0);

        Notification notification = new NotificationCompat.Builder(this, AppStart.CHANNEL_SportsID)
                .setSmallIcon(R.drawable.noti)
                .setContentTitle(t)
                .setContentText(m)
                .setDefaults(Notification.DEFAULT_SOUND)//not needed +Android v8(Oreo)... picks sound from Priority
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)

                .setContentIntent(openActivityIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();

        notificationManager.notify(1, notification);
    }

    public void withButton_Intent(View v){
        String t = title.getText().toString();
        String m = msg.getText().toString();

//for Opening an activity
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent openActivityIntent = PendingIntent.getActivity(this,0, i, 0);

//for Yes button... pending intents Request codes have to be different
        Intent broadcastIntent1 = new Intent(this, MyNotificationReceiver.class);
        broadcastIntent1.putExtra("Message", "YES From BroadCastReceiver");
        PendingIntent buttonIntent1 = PendingIntent.getBroadcast(this,123, broadcastIntent1, PendingIntent.FLAG_UPDATE_CURRENT);
//for NO button... pending intents Request codes have to be different
        Intent broadcastIntent2 = new Intent(this, MyNotificationReceiver.class);
        broadcastIntent2.putExtra("Message", "NO From BroadCastReceiver");
        PendingIntent buttonIntent2 = PendingIntent.getBroadcast(this,789, broadcastIntent2, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new NotificationCompat.Builder(this, AppStart.CHANNEL_SportsID)
                .setSmallIcon(R.drawable.noti)
                .setContentTitle(t)
                .setContentText(m)
                .setColor(Color.GREEN)
                .setDefaults(Notification.DEFAULT_SOUND)//not needed +Android v8(Oreo)... picks sound from Priority
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)

                .setContentIntent(openActivityIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.ic_launcher, "YES Do It!", buttonIntent1)
                .addAction(R.mipmap.ic_launcher, "No DONT!", buttonIntent2)//icons only in Wearables
                .build();

        notificationManager.notify(1, notification);

    }

    public void BigNotification(View v){
        String t = title.getText().toString();
        String m = msg.getText().toString();

//for Opening an activity
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent openActivityIntent = PendingIntent.getActivity(this,0, i, 0);

        Notification notification = new NotificationCompat.Builder(this, AppStart.CHANNEL_SportsID)
                .setSmallIcon(R.drawable.noti)
                .setContentTitle(t)
                .setContentText(m)

                .setLargeIcon(largeIcon)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(longText)
                        .setBigContentTitle("Title for Large Text")
                        .setSummaryText("A small Summary"))

                .setColor(Color.GREEN)
                .setDefaults(Notification.DEFAULT_SOUND)//not needed +Android v8(Oreo)... picks sound from Priority
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(openActivityIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(1, notification);

    }

    public void CheckNotifications(View v){
        if (!notificationManager.areNotificationsEnabled()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                startActivity(intent);
            } else {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }
    }


    @RequiresApi(26)
    public void CheckSportsChannel(View v){
        newCheckingIdea();//channels available only in and after Oreo
    }

    @RequiresApi(26)
    public void newCheckingIdea(){
        NotificationManager manager = getSystemService(NotificationManager.class);
        NotificationChannel channel = manager.getNotificationChannel(AppStart.CHANNEL_SportsID);

        if(channel != null && channel.getImportance() == NotificationManager.IMPORTANCE_NONE){
            Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, AppStart.CHANNEL_SportsID);
            startActivity(intent);
        }

        Log.e("ChannelCheck","if you see this... means you are in Oreo!");
    }

public void Activity2(View v){
    Intent intent = new Intent(this,Main2Activity.class);
    startActivity(intent);
}





}