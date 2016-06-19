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



        Toast.makeText(context, "wake up 87",
                Toast.LENGTH_LONG).show();
    }
}
