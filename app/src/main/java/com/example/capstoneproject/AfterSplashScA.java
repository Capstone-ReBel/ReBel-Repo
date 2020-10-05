package com.example.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AfterSplashScA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_after_splash_sc );

        getSupportActionBar().hide();
    }

    public void slider(View view) {
        startActivity( new Intent( getApplicationContext(), intro.class ) );
    }
}