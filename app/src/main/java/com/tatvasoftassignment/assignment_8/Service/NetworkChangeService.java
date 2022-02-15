package com.tatvasoftassignment.assignment_8.Service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;

import com.tatvasoftassignment.assignment_8.BroadcastReceiver.NetworkChangeReceiver;

public class NetworkChangeService extends Service {
    NetworkChangeReceiver rec = new NetworkChangeReceiver();

    public NetworkChangeService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(rec, intentFilter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(rec);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}