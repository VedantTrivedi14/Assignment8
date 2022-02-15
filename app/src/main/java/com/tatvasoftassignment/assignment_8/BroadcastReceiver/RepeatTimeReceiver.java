package com.tatvasoftassignment.assignment_8.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tatvasoftassignment.assignment_8.Activity.MainActivity;
import com.tatvasoftassignment.assignment_8.Activity.NotifyDescriptionActivity;
import com.tatvasoftassignment.assignment_8.R;

public class RepeatTimeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        MainActivity.createNotification(context,
                R.drawable.ic_baseline_alarm_24,
                context.getString(R.string.r_time_title),
                context.getString(R.string.r_time_msg),
                context.getString(R.string.r_time_longText), 5,
                NotifyDescriptionActivity.class);
    }
}