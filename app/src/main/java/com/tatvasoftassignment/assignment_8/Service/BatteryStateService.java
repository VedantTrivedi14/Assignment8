package com.tatvasoftassignment.assignment_8.Service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.tatvasoftassignment.assignment_8.BroadcastReceiver.BatteryStateReceiver;

public class BatteryStateService extends Service {
    BatteryStateReceiver rec = new BatteryStateReceiver();

    public BatteryStateService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW);
        intentFilter.addAction(Intent.ACTION_BATTERY_OKAY);
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