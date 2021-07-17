package com.example.googlenewsfeedapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.googlenewsfeedapp.adapter.CategoryAdapter;
import com.example.googlenewsfeedapp.adapter.NewsAdapter;
import com.example.googlenewsfeedapp.api.APIInterface;
import com.example.googlenewsfeedapp.model.ArticleModelClass;
import com.example.googlenewsfeedapp.model.CategoryModelClass;
import com.example.googlenewsfeedapp.model.NewsModelClass;
import com.example.googlenewsfeedapp.model.SourceModelClass;
import com.leo.simplearcloader.SimpleArcLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.CategoryClickInterface {

    public static final String API_Key = "4a1effb7c4f04be98feeca6aa7917dd1";
    // https://newsapi.org/v2/top-headlines/sources?apiKey=API_KEY (all sources)
    // https://newsapi.org/v2/top-headlines/sources?category=business&apiKey=4a1effb7c4f04be98feeca6aa7917dd1 (Business sources)
    // https://newsapi.org/v2/top-headlines/sources?country=us&apiKey=4a1effb7c4f04be98feeca6aa7917dd1 (sources in USA)
    // https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=4a1effb7c4f04be98feeca6aa7917dd1 (top headlines across globe by BBC)
    // https://newsapi.org/v2/top-headlines?country=us&apiKey=4a1effb7c4f04be98feeca6aa7917dd1 (top headlines across globe in US)
    // https://newsapi.org/v2/top-headlines?country=de&category=business&apiKey=4a1effb7c4f04be98feeca6aa7917dd1 (Top business headlines from Germany)
    // https://newsapi.org/v2/top-headlines?q=trump&apiKey=4a1effb7c4f04be98feeca6aa7917dd1 (Top headlines about Trump )
    // https://newsapi.org/v2/top-headlines?country=us&category=science&apiKey=4a1effb7c4f04be98feeca6aa7917dd1 (science news from USA)

    @BindView(R.id.newsRecyclerView)
    RecyclerView newsRecyclerView;
    @BindView(R.id.categoriesRecyclerView)
    RecyclerView categoriesRecyclerView;
    @BindView(R.id.arcLoader)
    SimpleArcLoader arcLoader;

    private List<ArticleModelClass> articleModelClassList;
    private List<CategoryModelClass> categoryModelClassList;
    private NewsAdapter newsAdapter;
    private CategoryAdapter categoryAdapter;

    String sports="https://images.unsplash.com/photo-1484482340112-e1e2682b4856?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=755&q=80";
    String headlines="https://images.unsplash.com/photo-1572949645841-094f3a9c4c94?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80";
    String tech="https://images.unsplash.com/photo-1498050108023-c5249f4df085?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=752&q=80";
    String business="https://images.unsplash.com/photo-1591696205602-2f950c417cb9?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80";
    String health="https://images.unsplash.com/photo-1528498033373-3c6c08e93d79?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=332&q=80";
    String entertainment="https://images.unsplash.com/photo-1603190287605-e6ade32fa852?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        articleModelClassList = new ArrayList<>();
        newsAdapter = new NewsAdapter(MainActivity.this,articleModelClassList);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsRecyclerView.setAdapter(newsAdapter);

        categoryModelClassList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(MainActivity.this,categoryModelClassList,this::onCategoryClicked);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoriesRecyclerView.setAdapter(categoryAdapter);
        getCategories();

        getNews("Top Headlines");
        newsAdapter.notifyDataSetChanged();
    }

    private void getCategories(){
        categoryModelClassList.add(new CategoryModelClass("Top Headlines",headlines));
        categoryModelClassList.add(new CategoryModelClass("Technology",tech));
        categoryModelClassList.add(new CategoryModelClass("Business",business));
        categoryModelClassList.add(new CategoryModelClass("Sports",sports));
        categoryModelClassList.add(new CategoryModelClass("Health",health));
        categoryModelClassList.add(new CategoryModelClass("Entertainment",entertainment));

        categoryAdapter.notifyDataSetChanged();
    }

    private void getNews(String category){
        arcLoader.setVisibility(View.GONE);
        articleModelClassList.clear();

        String categoryURL="https://newsapi.org/v2/top-headlines?country=in&category=" + category + "&apiKey=4a1effb7c4f04be98feeca6aa7917dd1";
        String topHeadlinesURL="https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=4a1effb7c4f04be98feeca6aa7917dd1";
        String baseURL="https://newsapi.org/";

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        APIInterface apiInterface = retrofit.create(APIInterface.class);
        Call<NewsModelClass> call;
        if(category.equals("Top Headlines")){
            call = apiInterface.getAllNews(topHeadlinesURL);
        }else{
            call = apiInterface.getNewsByCategory(categoryURL);
        }
        call.enqueue(new Callback<NewsModelClass>() {
            @Override
            public void onResponse(Call<NewsModelClass> call, Response<NewsModelClass> response) {
                NewsModelClass newsModel = response.body();
                arcLoader.setVisibility(View.GONE);

                if(response.isSuccessful() && Objects.requireNonNull(response.body()).getArticleModelClassList()!=null){
                    List<ArticleModelClass> articleModelList = Objects.requireNonNull(newsModel).getArticleModelClassList();
                    for(int i=0;i<articleModelList.size();i++){
                        String author=articleModelList.get(i).getAuthor();
                        String title=articleModelList.get(i).getTitle();
                        String desc=articleModelList.get(i).getDescription();
                        String url=articleModelList.get(i).getUrl();
                        String urlToImage=articleModelList.get(i).getUrlToImage();
                        String publishedAt=articleModelList.get(i).getPublishedAt();
                        String content=articleModelList.get(i).getContent();
                        SourceModelClass source=articleModelList.get(i).getSource();

                        articleModelClassList.add(new ArticleModelClass(author,title,desc,url,urlToImage,publishedAt,content,source));
                    }
                    newsAdapter.notifyDataSetChanged();
               }else{
                    Toast.makeText(MainActivity.this,"Error while retrieving News",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NewsModelClass> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error while retrieving News",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCategoryClicked(int position) {
        String category=categoryModelClassList.get(position).getCategoryName();
        getNews(category);
    }
}