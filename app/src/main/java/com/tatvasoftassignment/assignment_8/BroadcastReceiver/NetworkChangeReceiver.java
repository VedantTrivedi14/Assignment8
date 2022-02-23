package com.tatvasoftassignment.assignment_8.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.tatvasoftassignment.assignment_8.Activity.MainActivity;
import com.tatvasoftassignment.assignment_8.Activity.NotifyDescriptionActivity;
import com.tatvasoftassignment.assignment_8.R;

public class NetworkChangeReceiver extends BroadcastReceiver {
    int smallIcon;
    String msg, title, longText;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                smallIcon = R.drawable.ic_baseline_wifi_24;
                title = context.getString(R.string.wifi_status);
                msg = context.getString(R.string.wifi_on_msg);
                longText = context.getString(R.string.wifi_on_longText);
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                smallIcon = R.drawable.ic_baseline_network_cell_24;
                title = context.getString(R.string.mobile_data_on_status);
                msg = context.getString(R.string.mobile_data_on_msg);
                longText = context.getString(R.string.mobile_data_on_longText);
            }

        } else {
            smallIcon = R.drawable.ic_baseline_signal_cellular_off_24;
            title = context.getString(R.string.net_off_status);
            msg = context.getString(R.string.net_off_msg);
            longText = context.getString(R.string.net_off_longText);
        }
        MainActivity.createNotification(context, smallIcon, title, msg, longText, 4,4, NotifyDescriptionActivity.class);
    }

}