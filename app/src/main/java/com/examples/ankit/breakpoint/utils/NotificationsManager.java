package com.examples.ankit.breakpoint.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.examples.ankit.breakpoint.BreakPointApplication;
import com.examples.ankit.breakpoint.MainActivity;
import com.examples.ankit.breakpoint.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by ankit on 13/06/17.
 */

public class NotificationsManager {
    private static NotificationsManager manager;

    private NotificationsManager() {

    }

    public static NotificationsManager getInstance() {
        if (manager == null) {
            manager = new NotificationsManager();
        }

        return manager;
    }

    public void generateNotification(String title, String msg) {
        Context context = BreakPointApplication.getAppContext();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(title)
                        .setAutoCancel(true)
                        .setContentText(msg);
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        int notificationId = 001002;
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(notificationId, mBuilder.build());
    }
}
