package com.chris.illinibus.Notification;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationManagerCompat;

import com.chris.illinibus.MainActivity;
import com.chris.illinibus.R;

/**
 * Notification service
 * Created by chrisfang on 11/30/16.
 */

public class BusService extends IntentService{
    private static int NOTIFICATION_ID = 10;

    public BusService() {
        super("BusService");
    }

    /**
     * Send notification when notification service is called
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        // Create notification instance
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Bus Arriving");
        builder.setContentText("Your requested bus is coming within 1 minute");
        builder.setSmallIcon(R.drawable.bus);
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(notificationSound);

        // Send notification to the home screen
        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);
    }
}
