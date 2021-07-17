package com.example.googlenewsfeedapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.googlenewsfeedapp.R;
import com.example.googlenewsfeedapp.Util;
import com.example.googlenewsfeedapp.model.ArticleModelClass;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ArticleViewHolder> {

    private Context context;
    private List<ArticleModelClass> articleModelClassList;

    public NewsAdapter(Context context, List<ArticleModelClass> articleModelClassList) {
        this.context = context;
        this.articleModelClassList = articleModelClassList;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        return new ArticleViewHolder(view);
    }

    @SuppressLint({"CheckResult", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        ArticleModelClass articleModelClass = articleModelClassList.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Util.getRandomDrawbleColor());
        requestOptions.error(Util.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context).load(articleModelClass.getUrlToImage()).apply(requestOptions).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).transition(DrawableTransitionOptions.withCrossFade()).into(holder.newsImageView);

        holder.publishedAtTextView.setText(Util.DateFormat(articleModelClass.getPublishedAt()));
        holder.titleTextView.setText(articleModelClass.getTitle());
        holder.descriptionTextView.setText(articleModelClass.getDescription());
        holder.sourceTextView.setText(articleModelClass.getSource().getName());
        holder.timeTextView.setText(" \u2022 " + Util.DateToTimeFormat(articleModelClass.getPublishedAt()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(articleModelClass.getUrl()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleModelClassList.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {

        ImageView newsImageView;
        TextView publishedAtTextView, titleTextView, descriptionTextView, sourceTextView, timeTextView;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);

            newsImageView = itemView.findViewById(R.id.newsImageView);
            publishedAtTextView = itemView.findViewById(R.id.publishedAtTextView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            sourceTextView = itemView.findViewById(R.id.sourceTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}
