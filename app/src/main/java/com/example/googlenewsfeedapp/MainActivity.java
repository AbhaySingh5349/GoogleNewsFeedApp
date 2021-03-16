package com.example.googlenewsfeedapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.googlenewsfeedapp.adapter.NewsAdapter;
import com.example.googlenewsfeedapp.api.APIClient;
import com.example.googlenewsfeedapp.api.APIInterface;
import com.example.googlenewsfeedapp.model.ArticleModelClass;
import com.example.googlenewsfeedapp.model.NewsModelClass;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String API_Key = "4a1effb7c4f04be98feeca6aa7917dd1";

    @BindView(R.id.topHeadlinesRecyclerView)
    RecyclerView topHeadlinesRecyclerView;

    List<ArticleModelClass> articleModelClassList;
    NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        topHeadlinesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        topHeadlinesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        topHeadlinesRecyclerView.setNestedScrollingEnabled(false);

        loadTopHeadlines();
    }

    public void loadTopHeadlines(){
        APIInterface apiInterface = APIClient.getAPIClient().create(APIInterface.class);

        Call<NewsModelClass> call;
        String country = Util.getCountry();
        call = apiInterface.getTopHeadlines(country,API_Key);

        call.enqueue(new Callback<NewsModelClass>() {
            @Override
            public void onResponse(Call<NewsModelClass> call, Response<NewsModelClass> response) {
                if(response.isSuccessful() && response.body().getArticleModelClassList()!=null){
                    if(!articleModelClassList.isEmpty()){
                        articleModelClassList.clear();
                    }
                    articleModelClassList = response.body().getArticleModelClassList();
                    newsAdapter = new NewsAdapter(MainActivity.this,articleModelClassList);
                    topHeadlinesRecyclerView.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(MainActivity.this,"Error while retrieving News",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NewsModelClass> call, Throwable t) {

            }
        });
    }
}