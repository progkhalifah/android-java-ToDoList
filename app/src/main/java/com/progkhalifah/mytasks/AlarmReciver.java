package com.progkhalifah.mytasks;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.CalendarContract;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class AlarmReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //get id and message from intent
        int notification = intent.getIntExtra("notification", 0);
        String message = intent.getStringExtra("todo");
        String description = intent.getStringExtra("description");
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //when notification is tapped, call MyDaysTasks
        /*Intent snoozeIntent = new Intent(this, MyBroadcastReceiver.class);
        snoozeIntent.setAction(ACTION_SNOOZE);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);*/
        Intent maintIntent = new Intent(context, MydaysTasks.class); // TODO: 10/15/2021 here change intent to the activity that display the task
        maintIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentInten = PendingIntent.getActivity(context, 1, maintIntent, PendingIntent.FLAG_ONE_SHOT);

        Intent intenttocalendar = new Intent(Intent.ACTION_INSERT);
        intenttocalendar.setData(CalendarContract.Events.CONTENT_URI);
        intenttocalendar.putExtra(CalendarContract.Events.TITLE, message);
        intenttocalendar.putExtra(CalendarContract.Events.DESCRIPTION, description);
        intenttocalendar.putExtra(CalendarContract.Events.DTSTART, "Fir, Oct25");
//        startActivity(intent); // TODO: 10/15/2021 here change intent to the activity that display the task

        maintIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentImportance = PendingIntent.getActivity(context, 1, intenttocalendar, PendingIntent.FLAG_ONE_SHOT);



        NotificationManager myNotificatoinManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // prepare notification
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.ic_alarm_on) // TODO: 10/15/2021 change your logo of icon
                .setContentTitle(message)
                .setContentText(description)
                .setSubText("You told us to reminder you")
                .setColor(Color.RED)
                .setWhen(System.currentTimeMillis())
                .setSound(alarmSound)
                .setVisibility(Notification.VISIBILITY_PUBLIC);


        /*
        .addAction(R.drawable.ic_alarm_on,"My Task",contentInten)
                .addAction(R.drawable.ic_star,"My Importance",contentImportance)*/



//                        .setContentIntent(maintIntent)

        // notify
        myNotificatoinManager.notify((int) System.currentTimeMillis(), builder.build());



    }
}
