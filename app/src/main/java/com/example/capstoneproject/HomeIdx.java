package com.example.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.capstoneproject.API.ApiService;
import com.example.capstoneproject.API.Server;
import com.example.capstoneproject.Adapter.NewsAdapter;
import com.example.capstoneproject.Entity.News;
import com.example.capstoneproject.Entity.ResponseNews;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeIdx extends AppCompatActivity {

    private RecyclerView news;
    private NewsAdapter adapter;
    List<News> list = new ArrayList<>();
    ProgressDialog loading;
    ApiService api;

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

}