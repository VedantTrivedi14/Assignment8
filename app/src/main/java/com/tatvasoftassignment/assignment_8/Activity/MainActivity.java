package com.tatvasoftassignment.assignment_8.Activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.tatvasoftassignment.assignment_8.R;
import com.tatvasoftassignment.assignment_8.Service.BatteryStateService;
import com.tatvasoftassignment.assignment_8.Service.ChargingModeService;
import com.tatvasoftassignment.assignment_8.Service.GPSService;
import com.tatvasoftassignment.assignment_8.Service.NetworkChangeService;
import com.tatvasoftassignment.assignment_8.Service.ScreenService;
import com.tatvasoftassignment.assignment_8.Utils.Constants;
import com.tatvasoftassignment.assignment_8.BroadcastReceiver.RepeatTimeReceiver;
import com.tatvasoftassignment.assignment_8.BroadcastReceiver.SpecificAlarmReceiver;
import com.tatvasoftassignment.assignment_8.databinding.ActivityMainBinding;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private long timeInMillisecond = 0;
    private int timeInHours = 0;
    private int timeInMin = 0;
    Calendar mCurrentTime = Calendar.getInstance();
    PendingIntent pd;
    AlarmManager alarmManager;
    TimePickerDialog.OnTimeSetListener timeSetListener;
    TimePickerDialog timePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent i = new Intent();
            String packageName = getPackageName();
            PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            if (!powerManager.isIgnoringBatteryOptimizations(packageName)) {
                i.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                i.setData(Uri.parse("package:" + packageName));
                startActivity(i);
            }
        }

        binding.switchCharge.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Intent intent = new Intent(this, ChargingModeService.class);
            if (isChecked) {
                startService(intent);

            } else {
                stopService(intent);
            }

        });

        binding.switchBatteryLow.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Intent intent = new Intent(this, BatteryStateService.class);
            if (isChecked) {
                startService(intent);

            } else {
                stopService(intent);
            }

        });

        binding.switchGps.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Intent intent = new Intent(this, GPSService.class);
            if (isChecked) {
                startService(intent);

            } else {
                stopService(intent);
            }
        });

        binding.switchNetwork.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Intent intent = new Intent(this, NetworkChangeService.class);
            if (isChecked) {
                startService(intent);

            } else {
                stopService(intent);
            }
        });


        binding.switchIdle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Intent intent = new Intent(this, ScreenService.class);
            if (isChecked) {
                startService(intent);

            } else {
                stopService(intent);
            }
        });
        timeSetListener = (view, hourOfDay, minute) -> {
            mCurrentTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mCurrentTime.set(Calendar.MINUTE, minute);
            timeInMillisecond = mCurrentTime.getTimeInMillis();
            timeInHours = mCurrentTime.get(Calendar.HOUR_OF_DAY);
            timeInMin = mCurrentTime.get(Calendar.MINUTE);
            binding.etAlarm.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(mCurrentTime.getTime()));
        };

        binding.etAlarm.setOnClickListener(v1 -> {
            timePickerDialog = new TimePickerDialog(MainActivity.this,
                    timeSetListener,
                    mCurrentTime.get(Calendar.HOUR_OF_DAY),
                    mCurrentTime.get(Calendar.MINUTE), false);
            timePickerDialog.show();

        });


        binding.etRepeatingTime.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        binding.switchAlarm.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Calendar cal = Calendar.getInstance();
                long currentTimeMilliSecond = cal.getTimeInMillis();
                if (!binding.etAlarm.getText().toString().isEmpty() && timeInMillisecond != 0 && currentTimeMilliSecond < timeInMillisecond) {
                    setAlarm(timeInMillisecond);
                } else if (binding.etAlarm.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, getString(R.string.Set_specific_time), Toast.LENGTH_SHORT).show();
                    binding.switchAlarm.setChecked(false);
                }
            } else if (pd != null && alarmManager != null) {
                alarmManager.cancel(pd);
                binding.etAlarm.getText().clear();
            }
        });

        binding.switchRepeatTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            PendingIntent pd;

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!(binding.etRepeatingTime.getText()).toString().isEmpty()) {
                        int RepeatTime = Integer.parseInt(binding.etRepeatingTime.getText().toString());
                        Intent intent = new Intent(MainActivity.this, RepeatTimeReceiver.class);
                        pd = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (RepeatTime * 1000L), pd);
                    } else {
                        Toast.makeText(MainActivity.this, getString(R.string.SetTime), Toast.LENGTH_SHORT).show();
                        binding.switchRepeatTime.setChecked(false);
                    }
                } else if (pd != null) {
                    alarmManager.cancel(pd);
                    binding.etRepeatingTime.setText(R.string.set_default_time);
                }
            }
        });


    }


    public static void createNotification(Context context, int smallIcon, String
            title, String text, String longText, int id,int reqCode, Class activity) {
        NotificationManagerCompat mNotify = NotificationManagerCompat.from(context);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Constants.ID, context.getString(R.string.notification), NotificationManager.IMPORTANCE_HIGH);
            mNotify.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constants.ID);
        builder.setSmallIcon(smallIcon);
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);

        Intent intent = new Intent(context, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.NOTIFICATION_ICON, smallIcon);
        intent.putExtra(Constants.NOTIFICATION_TITLE, title);
        intent.putExtra(Constants.NOTIFICATION_MSG_TEXT, text);
        intent.putExtra(Constants.NOTIFICATION_LONG_TEXT, longText);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, reqCode,intent, PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        mNotify.notify(id, builder.build());
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sh = getSharedPreferences(Constants.SHRED_PREFERENCES, MODE_PRIVATE);
        binding.switchCharge.setChecked(sh.getBoolean(Constants.CHARGE, false));
        binding.switchBatteryLow.setChecked(sh.getBoolean(Constants.LOW_BATTERY, false));
        binding.switchNetwork.setChecked(sh.getBoolean(Constants.NETWORK, false));
        binding.switchGps.setChecked(sh.getBoolean(Constants.GPS, false));
        binding.switchIdle.setChecked(sh.getBoolean(Constants.IDLE, false));

        if (sh.getBoolean(Constants.SWITCH_ALARM, false)) {
            binding.etAlarm.setText(sh.getString(Constants.ALARM_TIME, ""));
            timeInMillisecond = sh.getLong(Constants.ALARM_TIME_IN_MILLI_SEC, 0);
            timeInHours = sh.getInt(Constants.TIME_IN_HOURS, 0);
            timeInMin = sh.getInt(Constants.TIME_IN_MIN, 0);

            binding.etAlarm.setOnClickListener(v1 -> {
                binding.switchAlarm.setChecked(false);
                timePickerDialog = new TimePickerDialog(MainActivity.this,
                        timeSetListener,
                        timeInHours,
                        timeInMin, false);
                timePickerDialog.show();
            });

        }

        binding.switchAlarm.setChecked(sh.getBoolean(Constants.SWITCH_ALARM, false));


        binding.etRepeatingTime.setText(String.valueOf(sh.getInt(Constants.TIME_STEP, 0)));
        binding.switchRepeatTime.setChecked(sh.getBoolean(Constants.REPEAT, false));

    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sh = getSharedPreferences(Constants.SHRED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putBoolean(Constants.CHARGE, binding.switchCharge.isChecked());
        editor.putBoolean(Constants.LOW_BATTERY, binding.switchBatteryLow.isChecked());
        editor.putBoolean(Constants.NETWORK, binding.switchNetwork.isChecked());
        editor.putBoolean(Constants.GPS, binding.switchGps.isChecked());
        editor.putBoolean(Constants.IDLE, binding.switchIdle.isChecked());
        editor.putBoolean(Constants.SWITCH_ALARM, binding.switchAlarm.isChecked());
        if (binding.switchAlarm.isChecked()) {
            editor.putString(Constants.ALARM_TIME, binding.etAlarm.getText().toString());
            editor.putLong(Constants.ALARM_TIME_IN_MILLI_SEC, timeInMillisecond);
            editor.putInt(Constants.TIME_IN_HOURS, timeInHours);
            editor.putInt(Constants.TIME_IN_MIN, timeInMin);
        }
        editor.putInt(Constants.TIME_STEP, Integer.parseInt((binding.etRepeatingTime.getText()).toString()));
        editor.putBoolean(Constants.REPEAT, binding.switchRepeatTime.isChecked());
        editor.apply();

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void setAlarm(long time) {
        Intent intent = new Intent(MainActivity.this, SpecificAlarmReceiver.class);
        pd = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pd);

    }

}

