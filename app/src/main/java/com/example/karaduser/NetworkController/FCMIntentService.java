package com.example.karaduser.NetworkController;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.karaduser.Activity.NotificationActivity;
import com.example.karaduser.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class FCMIntentService extends FirebaseMessagingService {

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    private String date = "";
    private String title = "Karad User", image;
    private String image_path, json_image_path;
    private JSONObject jsonObject;
    String from;

    @Override
    public void onMessageReceived(RemoteMessage message) {
        Log.d("message1", "onMessageReceived: " + message.getFrom());
        Log.d("message2", "onMessageReceived: " + message.getData());
        Log.d("message3", "onMessageReceived: " + message.getNotification());
        String jsonStr = "";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("message", message.getNotification());
            jsonStr = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("jsonarr", "onMessageReceived: " + jsonStr);


        showSmallNotification(title, message.getNotification().getBody(), null);

    }

    @Override
    public void onNewToken(String token) {
        Log.d("new_token", "Refreshed token: " + token);
    }

    public String sendRegistrationToServer(String token) {
        return token;
    }


    public void showSmallNotification(String title, String message, Intent intent) {

        Intent i = new Intent(this, NotificationActivity.class);
        Bundle bundle = new Bundle();
        i.putExtras(bundle);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        int requestCode = ("someString" + System.currentTimeMillis()).hashCode();
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, i, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        requestCode,
                        i,
                        PendingIntent.FLAG_MUTABLE
                );
        String NOTIFICATION_CHANNEL_ID = "my_app"; // default_channel_id
        NotificationCompat.Builder builder;
//samll notification
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.applogop);
        builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(Html.fromHtml(message))
                .setSmallIcon(R.mipmap.ic_launcher_logo)
                .setLargeIcon(icon)
                .setContentIntent(resultPendingIntent)
                .setVibrate(new long[]{100, 250, 100, 250, 100, 250})
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel nChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
            nChannel.enableLights(true);
            assert notificationManager != null;
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            builder.setPriority(NotificationCompat.PRIORITY_MAX);
            notificationManager.createNotificationChannel(nChannel);

        }
        assert notificationManager != null;
        Notification notification = builder.build();
        notificationManager.notify(requestCode, notification);
    }

}