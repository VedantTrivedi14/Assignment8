package com.tatvasoftassignment.assignment_8.Service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.tatvasoftassignment.assignment_8.BroadcastReceiver.ChargingModeReceiver;

public class ChargingModeService extends Service {
    ChargingModeReceiver rec = new ChargingModeReceiver();

    public ChargingModeService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
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