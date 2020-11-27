package com.example.capstoneproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.util.Objects;

public class SplashScA extends AppCompatActivity {
    //private Handler splash = new Handler();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash_sc );

        Objects.requireNonNull( getSupportActionBar() ).hide();
        new Handler( Looper.getMainLooper() ).postDelayed( new Runnable() {
            @Override
            public void run() {
                Intent Intent = new Intent( SplashScA.this, HomeIdx.class );
                startActivity( Intent );
                finish();
            }

        }, 4000 );

    }

}

