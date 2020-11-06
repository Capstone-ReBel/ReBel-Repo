package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.capstoneproject.API.ApiService;
import com.example.capstoneproject.API.Server;
import com.example.capstoneproject.Adapter.NewsAdapter;
import com.example.capstoneproject.Entity.News;
import com.example.capstoneproject.Entity.ResponseNews;
import com.example.capstoneproject.Kategori.Business;
import com.example.capstoneproject.Kategori.Entertainment;
import com.example.capstoneproject.Kategori.Health;
import com.example.capstoneproject.Kategori.Science;
import com.example.capstoneproject.Kategori.Sports;
import com.example.capstoneproject.Kategori.Technology;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeIdx extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView news;
    private NewsAdapter adapter;
    List<News> list = new ArrayList<>();
    ProgressDialog loading;
    ApiService api;
    private static final int TIME_LIMIT = 1800;
    private static long backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_homeidx );

        news = findViewById( R.id.news );
        api = Server.getApiService();
        adapter = new NewsAdapter( HomeIdx.this, list );

        news.setHasFixedSize( true );
        news.setLayoutManager( new LinearLayoutManager( HomeIdx.this ) );
        news.setAdapter( adapter );
        refresh();

        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
    }

    public void refresh() {
        loading = new ProgressDialog( HomeIdx.this );
        loading.setCancelable( false );
        loading.setMessage( "Loading..." );
        showDialog();
        api.getItem( "12850cd010b54441aaeff6749dc99cd0" ).enqueue( new Callback<ResponseNews>() {
            @Override
            public void onResponse(Call<ResponseNews> call, Response<ResponseNews> response) {
                if (response.isSuccessful()) {
                    hideDialog();
                    list = response.body().getNewsList();
                    news.setAdapter( new NewsAdapter( HomeIdx.this, list ) );
                    adapter.notifyDataSetChanged();
                } else {
                    hideDialog();
                    Toast.makeText( HomeIdx.this, "Gagal mengambil data !", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseNews> call, Throwable t) {
                hideDialog();
                Toast.makeText( HomeIdx.this, "Gagal menyambung ke internet !", Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    private void showDialog() {
        if (!loading.isShowing())
            loading.show();
    }

    private void hideDialog() {
        if (loading.isShowing())
            loading.dismiss();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {

            if (TIME_LIMIT + backPressed > System.currentTimeMillis()) {
                // super.onBackPressed();
                moveTaskToBack( true );
            } else {
                Toast.makeText( this, "Press back again to exit.", Toast.LENGTH_SHORT ).show();
            }
            backPressed = System.currentTimeMillis();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.business) {
            Intent bus = new Intent( HomeIdx.this, Business.class );
            startActivity( bus );
            overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
        } else if (id == R.id.entertainment) {
            Intent enter = new Intent( HomeIdx.this, Entertainment.class );
            startActivity( enter );
            overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
        } else if (id == R.id.health) {
            Intent heal = new Intent( HomeIdx.this, Health.class );
            startActivity( heal );
            overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
        } else if (id == R.id.science) {
            Intent scien = new Intent( HomeIdx.this, Science.class );
            startActivity( scien );
            overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
        } else if (id == R.id.sports) {
            Intent sport = new Intent( HomeIdx.this, Sports.class );
            startActivity( sport );
            overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
        } else if (id == R.id.technology) {
            Intent tech = new Intent( HomeIdx.this, Technology.class );
            startActivity( tech );
            overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
        }

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }
}