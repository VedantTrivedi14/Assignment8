package com.tatvasoftassignment.assignment_8.Service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.tatvasoftassignment.assignment_8.BroadcastReceiver.ScreenReceiver;

public class ScreenService extends Service {
    ScreenReceiver rec = new ScreenReceiver();

    public ScreenService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
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