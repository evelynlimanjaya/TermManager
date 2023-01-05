package com.project.termmanager.UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.project.termmanager.R;

public class CourseAlertReceiver extends BroadcastReceiver {
    String channelId = "courseAlert";
    static int notificationID;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getStringExtra("courseKey"), Toast.LENGTH_LONG).show();
        createNotificationChannel(context, channelId);
        Notification n = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_baseline_access_time_24)
                .setContentText(intent.getStringExtra("courseKey"))
                .setContentTitle("Course Alert").build();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notificationID++, n);

    }

    private void createNotificationChannel(Context context, String CHANNEL_ID){
        CharSequence name = context.getResources().getString(R.string.channel_name);
        String description = context.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager nm = context.getSystemService(NotificationManager.class);
        nm.createNotificationChannel(channel);

    }
}