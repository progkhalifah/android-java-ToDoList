package com.progkhalifah.mytasks.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.progkhalifah.mytasks.MydaysTasks;
import com.progkhalifah.mytasks.R;

public class ExampleAppWidgetProvider extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Perform this loop procedure for each widget that belongs to this
        // provider.
        for (int i=0; i < appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];
            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, MydaysTasks.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    /* context = */ context,
                    /* requestCode = */ 0,
                    /* intent = */ intent,
                    /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            // Get the layout for the widget and attach an on-click listener
            // to the button.
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_loading_appwidget);
            views.setOnClickPendingIntent(R.id.widget_myDayTask, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget.
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

}
