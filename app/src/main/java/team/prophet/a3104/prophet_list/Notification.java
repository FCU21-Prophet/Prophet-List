package team.prophet.a3104.prophet_list;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by acer on 2016/6/20.
 */
public class Notification extends BroadcastReceiver {
    static int id = 70000;

    @Override
    public void onReceive(Context context, Intent intent) {

        String title = intent.getStringExtra("TITLE_RESULT");
        String content = intent.getStringExtra("CONTENT_RESULT");

        Intent newintent = new Intent();
        newintent.putExtra("TITLE_RESULT", title);
        newintent.putExtra("CONTENT_RESULT", content);
        newintent.setClass(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.
                getActivity(context, 0, newintent, PendingIntent.FLAG_ONE_SHOT);


        android.app.Notification notify = null;
        notify = newNotification(context, pendingIntent, title, content);

        NotificationManager notificationManager =
                (NotificationManager)context.
                        getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(id++, notify);
    }

    @SuppressLint("NewApi")
    private android.app.Notification newNotification(
            Context context, PendingIntent pi,
            String title, String msg) {

        android.app.Notification.Builder builder =
                new android.app.Notification.Builder(context);
        int defaults = 0;
        defaults |= android.app.Notification.DEFAULT_VIBRATE;
        defaults |= android.app.Notification.DEFAULT_SOUND;
        defaults |= android.app.Notification.DEFAULT_LIGHTS;
        builder.setDefaults(defaults);
        builder.setContentTitle(title);
        builder.setContentText(msg);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pi);
        builder.setTicker(msg);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        android.app.Notification notify = builder.build();
        return notify;
    }
}
