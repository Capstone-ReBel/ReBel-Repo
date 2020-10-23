package com.example.capstoneproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class AfterSplashScA extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_after_splash_sc );

        Objects.requireNonNull( getSupportActionBar() ).hide();
    }

    public void slider(View view) {
        startActivity( new Intent( getApplicationContext(), Intro.class ) );
    }
}