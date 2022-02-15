package com.tatvasoftassignment.assignment_8.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tatvasoftassignment.assignment_8.Activity.MainActivity;
import com.tatvasoftassignment.assignment_8.Activity.NotifyDescriptionActivity;
import com.tatvasoftassignment.assignment_8.R;

public class ScreenReceiver extends BroadcastReceiver {
    int smallIcon;
    String msg, title, longText;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            smallIcon = R.drawable.ic_baseline_phone_android_24;
            title = context.getString(R.string.non_idle_state);
            msg = context.getString(R.string.non_idle_msg);
            longText = context.getString(R.string.non_idle_longText);

        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            smallIcon = R.drawable.ic_baseline_mobile_off_24;
            title = context.getString(R.string.idle_state);
            msg = context.getString(R.string.idle_state_msg);
            longText = context.getString(R.string.idle_state_longText);

        }
        MainActivity.createNotification(context, smallIcon, title, msg, longText, 6, NotifyDescriptionActivity.class);
    }

}