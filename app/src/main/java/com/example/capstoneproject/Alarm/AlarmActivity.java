package com.example.capstoneproject.Alarm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstoneproject.HomeIdx;
import com.example.capstoneproject.R;

import java.util.Objects;

public class AlarmActivity extends AppCompatActivity {
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    private SharedPreferences shared;
    private static final String shared_name = "alarmshared";
    private static final String alarm_name = "AlarmSwitch";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_alarm );

        Objects.requireNonNull( getSupportActionBar() ).setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled( true );

        mNotificationManager = (NotificationManager)
                getSystemService( NOTIFICATION_SERVICE );


        shared = getSharedPreferences( shared_name, Context.MODE_PRIVATE );

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch alarmToggle = findViewById( R.id.AlarmSwitch );

        Intent notifyIntent = new Intent( this, Alarm.class );

        boolean alarmUp = getAlarm();
        alarmToggle.setChecked( alarmUp );

        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                ( this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT );

        final AlarmManager alarmManager = (AlarmManager) getSystemService( ALARM_SERVICE );


        alarmToggle.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton,
                                                 boolean isChecked) {
                        String toastMessage;
                        if (isChecked) {
                            long repeatInterval = 60 * 1440 * 1000;
                            long triggerTime = SystemClock.elapsedRealtime() + repeatInterval;

                            //If the Toggle is turned on, set the repeating alarm with a 15 minute interval
                            if (alarmManager != null) {
                                alarmManager.setRepeating
                                        ( AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, repeatInterval, notifyPendingIntent );
                            }

                            setAlarm( true );
                            //Set the toast message for the "on" case.
                            toastMessage = getString( R.string.SUAOn );
                        } else {
                            mNotificationManager.cancelAll();
                            if (alarmManager != null) {
                                alarmManager.cancel( notifyPendingIntent );
                            }
                            setAlarm( false );
                            //Set the toast message for the "off" case.
                            toastMessage = getString( R.string.SUAOff );
                        }

                        //Show a toast to say the alarm is turned on or off.
                        Toast.makeText( AlarmActivity.this, toastMessage, Toast.LENGTH_SHORT )
                                .show();
                    }
                } );

        createNotificationChannel();
    }

    public void setAlarm(boolean isChecked) {
        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean( alarm_name, isChecked );
        editor.apply();
    }

    public boolean getAlarm() {
        return shared.getBoolean( alarm_name, false );
    }

    public void createNotificationChannel() {
        // Create a notification manager object.
        mNotificationManager =
                (NotificationManager) getSystemService( NOTIFICATION_SERVICE );

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    ( PRIMARY_CHANNEL_ID,
                            "Stand up notification",
                            NotificationManager.IMPORTANCE_HIGH );
            notificationChannel.enableLights( true );
            notificationChannel.setLightColor( Color.RED );
            notificationChannel.enableVibration( true );
            notificationChannel.setDescription( "Notifies every 15 minutes to stand up and walk" );

            mNotificationManager.createNotificationChannel( notificationChannel );
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( AlarmActivity.this, HomeIdx.class );
        startActivity( intent );
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent( AlarmActivity.this, HomeIdx.class );
        startActivity( intent );
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
    }
}