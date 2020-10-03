package com.example.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScA extends AppCompatActivity {
    private Handler splash = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash_sc );

        getSupportActionBar().hide();
        splash.postDelayed( new Runnable() {
            @Override
            public void run() {
                Intent Intent = new Intent( SplashScA.this, AfterSplashScA.class );
                startActivity( Intent );
            }
        }, 4000 );
    }
}