package com.tatvasoftassignment.assignment_8.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tatvasoftassignment.assignment_8.Activity.MainActivity;
import com.tatvasoftassignment.assignment_8.Activity.NotifyDescriptionActivity;
import com.tatvasoftassignment.assignment_8.R;

public class SpecificAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        MainActivity.createNotification(context,
                R.drawable.ic_baseline_alarm_24,
                context.getString(R.string.predefine_alarm),
                context.getString(R.string.specific_alarm_msg),
                context.getString(R.string.specific_alarm_longText), 7,7,
                NotifyDescriptionActivity.class);
    }
}