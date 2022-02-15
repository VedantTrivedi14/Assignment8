package com.tatvasoftassignment.assignment_8.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tatvasoftassignment.assignment_8.R;
import com.tatvasoftassignment.assignment_8.Utils.Constants;
import com.tatvasoftassignment.assignment_8.databinding.ActivityNotifyDescriptionBinding;

public class NotifyDescriptionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_description);
        ActivityNotifyDescriptionBinding binding = ActivityNotifyDescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int icon = getIntent().getIntExtra(Constants.NOTIFICATION_ICON, 0);
        String title = getIntent().getExtras().getString(Constants.NOTIFICATION_TITLE);
        String msg = getIntent().getExtras().getString(Constants.NOTIFICATION_MSG_TEXT);
        String longText = (getIntent().getExtras().getString(Constants.NOTIFICATION_LONG_TEXT));

        binding.imageView.setImageResource(icon);
        binding.titleNotification.setText(title);
        binding.msgNotification.setText(msg);
        binding.longTextNotification.setText(longText);


    }
}