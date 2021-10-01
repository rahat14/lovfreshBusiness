package com.fruitvendorapp.fcm;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.fruitvendorapp.R;
import com.fruitvendorapp.utilities.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class NotificationUtils {
    private static final int NOTIFICATION_ID = 100;
    private static final String CHANNEL_ID = "my_channel_01";
    private static String TAG = NotificationUtils.class.getSimpleName();

    private Context mContext;

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }

   /* public void showNotificationMessage(String title, String message, String timeStamp, Intent intent) {
        showNotificationMessage(title, message, timeStamp, intent, null);
    }*/

    public void showNotificationMessage(final String title, final String message, final String timeStamp, Intent intent, PendingIntent resultPendingIntent) {
        // Check for empty push message
        if (TextUtils.isEmpty(message))
            return;
        // notification icon
        final int icon = R.drawable.heart_logo;
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        stackBuilder.addNextIntent(intent);

        resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        final Uri defaultSoundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
//                + "://" + mContext.getPackageName() + "/raw/notification");
        final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + mContext.getPackageName() + "/" + R.raw.sound);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, CHANNEL_ID);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setSound(alarmSound);
        showSmallNotification(mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound);
        // playNotificationSound();

    }

    private void showSmallNotification(NotificationCompat.Builder mBuilder, int icon, String title, String message, String timeStamp, PendingIntent resultPendingIntent, Uri alarmSound) {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(message);
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "my channel";
        Notification notification;
        int importance = NotificationManager.IMPORTANCE_HIGH;
        //  NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, CHANNEL_ID);
//        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
//                .setAutoCancel(true)
//                .setContentTitle(title)
//                .setContentIntent(resultPendingIntent)
//                .setSound(alarmSound)
//                .setStyle(inboxStyle)
//                .setDefaults(Notification.DEFAULT_SOUND)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setWhen(getTimeMilliSec(timeStamp))
//                .setSmallIcon(R.drawable.heart_logo)
//                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
//                .setContentText(message)
//                .setChannelId(CHANNEL_ID)
//                .build();
//
//        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
//                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
//            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
//            mChannel.setSound(alarmSound, audioAttributes);
//            notificationManager.createNotificationChannel(mChannel);
//        }
        if (new SessionManager(mContext).getSound()) {
            notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentIntent(resultPendingIntent)
                    .setSound(alarmSound)
                    .setStyle(inboxStyle)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setWhen(getTimeMilliSec(timeStamp))
                    .setSmallIcon(R.drawable.heart_logo)
                    .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                    .setContentText(message)
                    .setChannelId(CHANNEL_ID)
                    .build();
        } else {
            notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentIntent(resultPendingIntent)
                    .setStyle(inboxStyle)
                    .setSound(null)
                    .setNotificationSilent()
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setWhen(getTimeMilliSec(timeStamp))
                    .setSmallIcon(R.drawable.heart_logo)
                    .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                    .setContentText(message)
                    .setChannelId(CHANNEL_ID)
                    .build();
        }
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            if (new SessionManager(mContext).getSound()) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
                mChannel.setSound(alarmSound, audioAttributes);
            } else {
                mChannel.setSound(null, null);
            }
            notificationManager.createNotificationChannel(mChannel);
        }
        notificationManager.notify(NOTIFICATION_ID, notification);
        Log.e("Notificationhelper", "NNNNNNNNNNNNNNNNNNNNNNN");
        if (new SessionManager(mContext).getSound()) {
            notification.defaults |= Notification.DEFAULT_SOUND;
        }

        if (new SessionManager(mContext).getVibrate()) {
            notification.defaults |= Notification.DEFAULT_VIBRATE;
        }
        notificationManager.notify(NOTIFICATION_ID, notification);

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            //mChannel.setDescription("description");
            notificationManager.createNotificationChannel(mChannel);
        }
        notificationManager.notify(NOTIFICATION_ID, notification);*/

    }


    // Playing notification sound
    public void playNotificationSound() {
        try {
            // Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
           // Uri defaultSoundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + mContext.getPackageName() + "/raw/notification");
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + mContext.getPackageName() + "/" + R.raw.sound);
            Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
            r.setStreamType(AudioManager.STREAM_ALARM);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method checks if the app is in background or not
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    // Clears notification tray messages
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    private static long getTimeMilliSec(String timeStamp) {
        if (timeStamp.length() > 0) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            try {
                Date date = format.parse(timeStamp);
                assert date != null;
                return date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
