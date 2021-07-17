package com.example.googlenewsfeedapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NewsModelClass {

    private String status;
    private int totalResults;
    private List<ArticleModelClass> articleModelClassList;

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

    public List<ArticleModelClass> getArticleModelClassList() {
        return articleModelClassList;
    }

    public void setArticleModelClassList(List<ArticleModelClass> articleModelClassList) {
        this.articleModelClassList = articleModelClassList;
    }

    public NewsModelClass(String status, int totalResults, List<ArticleModelClass> articleModelClassList) {
        this.status = status;
        this.totalResults = totalResults;
        this.articleModelClassList = articleModelClassList;
    }
/*  @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("totalResults")
    @Expose
    private int totalResults;

    @SerializedName("articles")
    @Expose
    private List<ArticleModelClass> articleModelClassList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResult() {
        return totalResults;
    }

    public void setTotalResult(int totalResult) {
        this.totalResults = totalResults;
    }

    public List<ArticleModelClass> getArticleModelClassList() {
        return articleModelClassList;
    }

    public void setArticleModelClassList(List<ArticleModelClass> articleModelClassList) {
        this.articleModelClassList = articleModelClassList;
    } */
}
