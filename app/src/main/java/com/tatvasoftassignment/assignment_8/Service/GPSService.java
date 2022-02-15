package com.tatvasoftassignment.assignment_8.Service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.IBinder;

import com.tatvasoftassignment.assignment_8.BroadcastReceiver.GPSReceiver;

public class GPSService extends Service {
    GPSReceiver rec = new GPSReceiver();

    public GPSService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
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