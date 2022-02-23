package com.tatvasoftassignment.assignment_8.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.tatvasoftassignment.assignment_8.Activity.MainActivity;
import com.tatvasoftassignment.assignment_8.Activity.NotifyDescriptionActivity;
import com.tatvasoftassignment.assignment_8.R;

public class GPSReceiver extends BroadcastReceiver {
    String title, msg, longText;
    int smallIcon;

    @Override
    public void onReceive(Context context, Intent intent) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            smallIcon = R.drawable.ic_baseline_location_on_24;
            title = context.getString(R.string.gps_enable_status);
            msg = context.getString(R.string.gps_on_msg);
            longText = context.getString(R.string.gps_on_longText);
        } else {
            smallIcon = R.drawable.ic_baseline_location_off_24;
            title = context.getString(R.string.gps_off);
            msg = context.getString(R.string.gps_off_msg);
            longText = context.getString(R.string.gps_off_longText);
        }
        MainActivity.createNotification(context, smallIcon, title, msg, longText, 3,3, NotifyDescriptionActivity.class);
    }

}