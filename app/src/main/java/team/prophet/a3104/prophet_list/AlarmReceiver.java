package team.prophet.a3104.prophet_list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by acer on 2016/6/19.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        String title = intent.getStringExtra("TITLE_RESULT");
        String content = intent.getStringExtra("CONTENT_RESULT");

        Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
        Intent notification_intent = new Intent();
        notification_intent.setAction("PROPHET");
        notification_intent.putExtra("TITLE_RESULT", title);
        notification_intent.putExtra("CONTENT_RESULT", content);
        context.sendBroadcast(notification_intent);
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
}
