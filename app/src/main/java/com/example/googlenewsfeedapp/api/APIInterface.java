package com.example.googlenewsfeedapp.api;

import com.example.googlenewsfeedapp.model.NewsModelClass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIInterface {

 /*   @GET("top-headlines")
    Call<NewsModelClass> getTopHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    ); */

    @GET
    Call<NewsModelClass> getAllNews(@Url String url);

    @GET
    Call<NewsModelClass> getNewsByCategory(@Url String url);
}
