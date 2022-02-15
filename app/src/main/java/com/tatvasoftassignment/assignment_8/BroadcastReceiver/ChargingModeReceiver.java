package com.tatvasoftassignment.assignment_8.BroadcastReceiver;

import static com.tatvasoftassignment.assignment_8.R.string.charge_status;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tatvasoftassignment.assignment_8.Activity.MainActivity;
import com.tatvasoftassignment.assignment_8.Activity.NotifyDescriptionActivity;
import com.tatvasoftassignment.assignment_8.R;

public class ChargingModeReceiver extends BroadcastReceiver {

    String title, msg, longText;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {

            title = context.getString(charge_status);
            msg = context.getString(R.string.charge_msg_on);
            longText = context.getString(R.string.charge_longText_on);
        } else if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
            title = context.getString(charge_status);
            msg = context.getString(R.string.charge_msg_off);
            longText = context.getString(R.string.charge_longText_off);
        }
        MainActivity.createNotification(context, R.drawable.ic_baseline_battery_charging_full_24, title, msg, longText, 2, NotifyDescriptionActivity.class);
    }

}