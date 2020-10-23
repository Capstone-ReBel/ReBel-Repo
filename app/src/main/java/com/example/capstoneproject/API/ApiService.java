package com.example.capstoneproject.API;

import com.example.capstoneproject.Entity.ResponseNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/v2/top-headlines/?country=id&category=business")
    Call<ResponseNews> getItem(@Query("apiKey") String apiKey);
}
