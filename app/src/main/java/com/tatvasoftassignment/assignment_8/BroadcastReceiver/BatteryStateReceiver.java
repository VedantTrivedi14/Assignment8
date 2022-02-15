package com.tatvasoftassignment.assignment_8.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tatvasoftassignment.assignment_8.Activity.MainActivity;
import com.tatvasoftassignment.assignment_8.Activity.NotifyDescriptionActivity;
import com.tatvasoftassignment.assignment_8.R;

public class BatteryStateReceiver extends BroadcastReceiver {

    String title, msg, longText;
    int smallIcon;

    @Override
    public void onReceive(Context context, Intent intent) {


        if (intent.getAction().equals(Intent.ACTION_BATTERY_LOW)) {
            smallIcon = R.drawable.ic_baseline_battery_alert_24;
            title = context.getString(R.string.battery_status);
            msg = context.getString(R.string.battery_msg_low);
            longText = context.getString(R.string.battery_longText_low);
        } else if (intent.getAction().equals(Intent.ACTION_BATTERY_OKAY)) {
            smallIcon = R.drawable.ic_baseline_battery_4_bar_24;
            title = context.getString(R.string.battery_status);
            msg = context.getString(R.string.battery_msg_okay);
            longText = context.getString(R.string.battery_longText_okay);
        }

        MainActivity.createNotification(context, smallIcon, title, msg, longText, 1, NotifyDescriptionActivity.class);
    }
}