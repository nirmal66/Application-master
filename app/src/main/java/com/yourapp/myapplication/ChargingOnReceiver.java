package com.yourapp.myapplication;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChargingOnReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {

        BatteryStatus bs = new BatteryStatus(context);
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        // http://stackoverflow.com/a/4047790
        Intent batteryIntent = context.getApplicationContext()
                .registerReceiver(null,
                        new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int rawlevel = batteryIntent.getIntExtra("level", -1);
        double scale = batteryIntent.getIntExtra("scale", -1);
        double level = -1;
        if (rawlevel >= 0 && scale > 0) {
            level = (rawlevel / scale) * 100;
        }
        String info = ("Current battery : " + (int) level + "%" + "\n");

        // http://stackoverflow.com/a/20648756
        DateFormat df = new SimpleDateFormat("EEE, MMM d, h:mm a");
        String date = df.format(Calendar.getInstance().getTime());
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
            // Do something when power connected
            bs.lastPlugin(date + " Battery status:" + (int) level + "%");

            info += ("Your phone is connected" + "\n");
            info += ("Last Plugin:" + date + "\n");
            if (sharedPrefs.getBoolean("pluginnotification", true))
                sendingNotification(context, info,"Your phone is connected");

        }
        if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
            // Do something when power disconnected
            bs.lastPlugout(date + " Battery status:" + (int) level + "%");

            info += ("Your phone is disconnected" + "\n");
            info += ("Last Plugout:" + date + "\n");
            if (sharedPrefs.getBoolean("plugoutnotification", true))
                sendingNotification(context, info,"Your phone is disconnected");
        }
        if (action.equals(Intent.ACTION_BATTERY_LOW)) {
            info += ("Connect your charger" + "\n");
            if (sharedPrefs.getBoolean("batterylownotification", true))
                sendingNotification(context, info,"Connect your charger.Battery low!!!!!");
        }

    }

    @SuppressLint("NewApi")
    private void sendingNotification(Context context, String info,String message) {
        Intent intent = new Intent(context, HomeActivity.class);
        // use System.currentTimeMillis() to have a unique ID for the pending
        // intent
        PendingIntent pIntent = PendingIntent.getActivity(context,
                (int) System.currentTimeMillis(), intent, 0);

        Uri alarmSound = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // build notification
        // the addAction re-use the same intent to keep the example short
        Notification n = new Notification.Builder(context)
                .setContentTitle("Battery Master")
                .setContentText(message)
                .setSmallIcon(R.drawable.notification)
                .setContentIntent(pIntent).setSound(alarmSound)
                .setStyle(new Notification.BigTextStyle().bigText(info))
                .setAutoCancel(true).build();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }
}



