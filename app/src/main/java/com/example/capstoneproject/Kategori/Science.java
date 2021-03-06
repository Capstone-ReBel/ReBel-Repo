package com.example.capstoneproject.Kategori;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;

import com.example.capstoneproject.API.ApiService;
import com.example.capstoneproject.API.Server;
import com.example.capstoneproject.Adapter.NewsAdapter;
import com.example.capstoneproject.Entity.News;
import com.example.capstoneproject.Entity.ResponseNews;
import com.example.capstoneproject.HomeIdx;
import com.example.capstoneproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class Science extends AppCompatActivity {

    private RecyclerView news;
    private NewsAdapter adapter;
    List<News> list = new ArrayList<>();
    final String category = "science";
    ProgressDialog loading;
    ApiService api;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_science );

        news = findViewById( R.id.news );
        api = Server.getApiService();
        adapter = new NewsAdapter( Science.this, list );

        news.setHasFixedSize( true );
        news.setLayoutManager( new LinearLayoutManager( Science.this ) );
        news.setAdapter( adapter );
        refresh();

        //membuat back button toolbar
        Objects.requireNonNull( getSupportActionBar() ).setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled( true );

    }

    public void refresh() {
        loading = new ProgressDialog( Science.this );
        loading.setCancelable( false );
        loading.setMessage( "Loading..." );
        showDialog();
        api.getListNews( category, "12850cd010b54441aaeff6749dc99cd0" ).enqueue( new Callback<ResponseNews>() {
            @Override
            public void onResponse(Call<ResponseNews> call, Response<ResponseNews> response) {
                if (response.isSuccessful()) {
                    hideDialog();
                    list = response.body().getNewsList();
                    news.setAdapter( new NewsAdapter( Science.this, list ) );
                    adapter.notifyDataSetChanged();
                } else {
                    hideDialog();
                    Toast.makeText( Science.this, "Gagal mengambil data !", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseNews> call, Throwable t) {
                hideDialog();
                Toast.makeText( Science.this, "Gagal menyambung ke internet !", Toast.LENGTH_SHORT ).show();
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
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( Science.this, HomeIdx.class );
        startActivity( intent );
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent( Science.this, HomeIdx.class );
        startActivity( intent );
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
    }
}