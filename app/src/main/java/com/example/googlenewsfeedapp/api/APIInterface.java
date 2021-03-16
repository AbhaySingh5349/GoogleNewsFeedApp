package com.example.googlenewsfeedapp.api;

import com.example.googlenewsfeedapp.model.NewsModelClass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("top-headlines")
    Call<NewsModelClass> getTopHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
}
