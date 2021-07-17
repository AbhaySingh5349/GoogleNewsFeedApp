package com.example.googlenewsfeedapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NewsModelClass {

    private String status;
    private int totalResults;
    private List<ArticleModelClass> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<ArticleModelClass> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleModelClass> articles) {
        this.articles = articles;
    }

    public NewsModelClass(String status, int totalResults, List<ArticleModelClass> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }
}
